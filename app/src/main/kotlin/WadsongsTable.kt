import org.jetbrains.exposed.v1.core.Table

/**
 * This is the Exposed DSL representation of the database table.
 * It is NOT the data entity; it is the schema mapping used by Exposed.
 */
object Wadsongs : Table("wadsongs") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 200)
    val artist = varchar("artist", 200)
    val year = integer("year")

    override val primaryKey = PrimaryKey(id)
}
