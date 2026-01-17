import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {

    /**
     * Connects to the SQLite database and ensures the required tables exist.
     * This centralises DB setup in one place, keeping the rest of the project clean.
     */
    fun init() {
        // SQLite creates the file if it does not exist.
        Database.connect("jdbc:sqlite:wadsongs.db", driver = "org.sqlite.JDBC")

        // Create tables if missing (idempotent schema initialisation).
        transaction {
            SchemaUtils.create(Wadsongs)
        }
    }
}
