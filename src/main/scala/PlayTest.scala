import play.api._
//import play.api.mvc._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.json.Format._
// you need this import to have combinators
import play.api.libs.functional.syntax._

object PlayTest {

  /*
  The trait Source defines any source related parameters.
  All sources of a particular type should have these required params.
   */
  trait SourceTypeConfig

  final case class RDBMSConfig(
      driverClass: String,
      connectionString: String,
      database: String,
      table: String,
      userName: String,
      password: String
  ) extends SourceTypeConfig
  object RDBMSConfig { implicit val fmt = Json.format[RDBMSConfig] }

  final case class DirectoryConfig(
      path: String,
      pathType: String // Local, gcloud, azure, aws, etc.
  ) extends SourceTypeConfig
  object DirectoryConfig { implicit val fmt = Json.format[DirectoryConfig] }

  /*
  The trait Format defines any file format related parameters.
   */
  trait FormatConfig

  final case class SQLConfig() extends FormatConfig
  object SQLConfig { implicit val fmt = Json.format[SQLConfig]}

  final case class CSVConfig(
      header: String,
      inferSchema: String,
      delimiter: String
  ) extends FormatConfig
  object CSVConfig { implicit val fmt = Json.format[CSVConfig]}

  final case class VCFConfig(generic: String) extends FormatConfig
  object VCFConfig { implicit val fmt = Json.format[CSVConfig]}

  final case class TextFileConfig(useUnicode: String) extends FormatConfig
  object TextFileConfig

  case class DataSource(
      name: String,
      organization: String,
      id: String,
      dataSize: String,
      sourceType: String,
      sourceTypeConfig: SourceTypeConfig,
      format: String,
      formatConfig: FormatConfig
  )

  object DataSource {

    implicit val dataSourceFormat = new Format[DataSource] {
      def reads(json: JsValue): JsResult[DataSource] = {
        val sourceType = (json \ "sourceType").validate[String]
        val format = (json \ "format").validate[String]



      }


    }

    def writes(d: DataSource): JsValue = {

    }
  }

  def main(args: Array[String]): Unit = {

    val input: JsValue = Json.parse(
      """
        {
        "name" : "test1",
        "sourceType" : "directory",
        "sourceTypeConfig" : {"path" : "gs://test/path", "pathType" "google"},
        "format" : "csv",
        "formatConfig" : {"header" : "yes", "inferSchema" : "yes",  "delimiter" :  "|"}
        }
      """
    )

    val inputResult = input.validate[DataSource]

    inputResult.fold(
      errors => println("NOOO"),
      in => println("YES!")
    )

    val nums = List(1, 2, 3)
    println(nums)
  }
}
