package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.model.Movie
import util.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun popularDao(): PopularDao
    abstract fun upcomingDao(): UpcomingDao
    abstract fun topRatedDao(): TopRatedDao
    abstract fun nowPlayingDao(): NowPlayingDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DATABASE_NAME
            )
                .addTypeConverter(ListConverter())
                .build()
        }
    }
}
