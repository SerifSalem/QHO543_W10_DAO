/**
 * Data entity:
 * A plain Kotlin object representing one row in the database table.
 *
 * Note:
 * - id is var because it is assigned after insertion (auto-increment).
 * - the rest are val because they represent stable song data.
 */
data class Song(
    var id: Int = 0,
    val title: String,
    val artist: String,
    val year: Int
)
