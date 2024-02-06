/*
 * OAI Server Interface
 * Copyright (C) 2020  Memoriav
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import sbt.*


object Dependencies {

  lazy val scalatestV = "3.1.2"

  lazy val alpakka = "com.lightbend.akka" %% "akka-stream-alpakka-file" % "7.0.1"

  lazy val akka = "com.typesafe.akka" %% "akka-stream" % "2.9.1"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % scalatestV

}
