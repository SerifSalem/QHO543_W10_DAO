/**
 * The rest of the application interacts ONLY with the DAO interface.
 * It does not use Exposed directly, which keeps the codebase maintainable.
 */
fun main() {
    DatabaseFactory.init()

    // Program depends on interface type; implementation can be swapped later.
    val dao: WadsongsDao = ExposedWadsongsDao()

    println("==== HitTastic DAO Demo ====")

    // 1) Insert songs
    val s1 = Song(title = "Numb", artist = "Linkin Park", year = 2003)
    val s2 = Song(title = "Billie Jean", artist = "Michael Jackson", year = 1982)
    val id1 = dao.insertSong(s1)
    val id2 = dao.insertSong(s2)

    println("Inserted songs with IDs: $id1, $id2")

    // 2) Read all
    println("\nAll songs:")
    dao.findAllSongs().forEach { println(it) }

    // 3) Read by id (demonstrates nullable return)
    println("\nFind by id ($id1):")
    val found = dao.findSongById(id1)
    println(found ?: "No song found (null)")

    // 4) Update
    println("\nUpdating song $id2...")
    val updatedCount = dao.updateSong(
        Song(id = id2, title = "Billie Jean (Remastered)", artist = "Michael Jackson", year = 1982)
    )
    println("Rows updated: $updatedCount")

    // 5) Delete
    println("\nDeleting song $id1...")
    val deletedCount = dao.deleteSong(id1)
    println("Rows deleted: $deletedCount")

    // 6) Final state
    println("\nAll songs after update/delete:")
    dao.findAllSongs().forEach { println(it) }

    println("\n==== End ====")
}
