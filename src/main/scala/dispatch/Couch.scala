package dispatch.couch

import java.io.InputStream
import java.net.URLEncoder.encode
import org.apache.http.HttpHost

import json._

trait Doc extends Js {
  val _id = Symbol("_id") ? str
  val _rev = Symbol("_rev") ? str
}
object Doc extends Doc

trait Couch {
  val hostname = "127.0.0.1"
  lazy val http = new Http(hostname, 5984)
}

class Database(val name: String) extends Couch with Js {
  def all_docs = http { /(name) / "_all_docs" j$ ( 'rows ! (list ! obj) ) } map ('id ! str)

  def create() { http( /(name) <<< Nil >|) }
  def delete() { http( /(name) <--() >|) }
}
/*
class Document(val db: Database, val id: String) extends Js {
  def apply() = db(encode(id))
}*/

/*
object Revise extends JsDef {
  val id = 'id as str
  val rev = 'rev as str

  def update(stream: InputStream, source: Js) =
    source(Doc._rev << Js(stream)(rev))
    
  def apply(source: Js) = new {
    def <<: (req: Http#Request) =
      (req <<< source.toString) >> (update(_, source))
  }
}
*/
