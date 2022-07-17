package com.example.mymusicplayer.data.source.local

import android.content.Context
import android.util.Log
import com.example.mymusicplayer.data.source.local.dao.SongDao
import com.example.mymusicplayer.data.source.local.entity.Song
import io.reactivex.Maybe
import io.reactivex.Single
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class LocalSongDataSourceImpl @Inject constructor(private val songDao: SongDao, private val applicationContext: Context) :
    LocalSongDataSource {


    override fun fetchSongs(): Single<Song>{
        return songDao.fetchAllSong()
    }

    override fun saveAudioToDeviceAndStorePath(song: Song, inputStream: InputStream) =
        storeSongInPhone(input = inputStream, fileName = fileName(song.audioUrl) )


    override fun storeSongInPhone(input: InputStream, fileName:String, folderName:String):String{
        try {
            val filePath = getFilePath(fileName, folderName)
            val fos = FileOutputStream(filePath)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return filePath
        }catch (e:Exception){
            Log.e("saveFile",e.toString())
        }
        finally {
            input?.close()
        }
        return ""
    }

    override fun fileName(url: String) =
        url.substring("url".lastIndexOf("/")+1)


    override fun getFilePath(fileName: String, folderName: String) =
        applicationContext.filesDir.absolutePath+folderName+fileName


    override fun storeSongInDb(songToStore: Song): Maybe<Long>{
       return songDao.storeSong(song = songToStore)
    }

    override fun obtainSongByTitle(titleOfSong: String): Single<Song> {
        return songDao.obtainSongByTitle(titleOfSong)
    }

    override fun setAsFavourite(song: Song): Maybe<Long> {
        return songDao.updateFavouriteSong(song)
    }
}