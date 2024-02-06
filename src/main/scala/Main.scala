import akka.NotUsed
import akka.actor.ActorSystem

import java.nio.file.Paths
import scala.concurrent.ExecutionContext
import akka.util.ByteString
import akka.stream.alpakka.file.scaladsl.Archive
import akka.stream.alpakka.file.ArchiveMetadata
import akka.stream.scaladsl.{BroadcastHub, FileIO, Sink, Source}
import akka.stream.Attributes



object Main {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem("AkkaStreamGraph")
    implicit val ec: ExecutionContext = system.dispatcher


    val source: Source[Int, NotUsed] = Source.unfold(0) {
      case a if a > 3 => None
      case a => Some(a + 1, compute(a))
    }.log(name = "original")
      .addAttributes(
        Attributes.logLevels(
          onElement = Attributes.LogLevels.Info,
          onFinish = Attributes.LogLevels.Info,
          onFailure = Attributes.LogLevels.Info))

    val dynamicBroadcast = BroadcastHub.sink[Int](bufferSize = 1)

    val materializedSource: Source[Int, NotUsed] = source.runWith(dynamicBroadcast).log(name = "myStream")
      .addAttributes(
        Attributes.logLevels(
          onElement = Attributes.LogLevels.Info,
          onFinish = Attributes.LogLevels.Info,
          onFailure = Attributes.LogLevels.Info))

    val s1: Source[Int, NotUsed] = materializedSource.map(i => i*2).log(name = "s1")
      .addAttributes(
        Attributes.logLevels(
          onElement = Attributes.LogLevels.Info,
          onFinish = Attributes.LogLevels.Info,
          onFailure = Attributes.LogLevels.Info))

    val s2: Source[Int, NotUsed] = materializedSource.map(i => i*1000).log(name = "s2")
      .addAttributes(
        Attributes.logLevels(
          onElement = Attributes.LogLevels.Info,
          onFinish = Attributes.LogLevels.Info,
          onFailure = Attributes.LogLevels.Info))


    val filesStream = Source(List(
      (ArchiveMetadata("s1.txt"), s1.map(a => ByteString(a.toString))),
      (ArchiveMetadata("s2.txt"), s2.map(a => ByteString(a.toString)))
    ))

    val res = filesStream.via(Archive.zip()).runWith(FileIO.toPath(Paths.get("result.zip")))

    res.onComplete { _ =>
      system.terminate()
    }
  }


  def compute(a: Int): Int = {
    //Thread.sleep(1000) // wait for 1000 millisecond
    println("wait " + a)
    a + 1
  }
}
