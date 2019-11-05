package com.funapp.wallpaperautochangeexample.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.funapp.wallpaperautochangeexample.R
import com.funapp.wallpaperautochangeexample.adapter.AdapterImage
import com.funapp.wallpaperautochangeexample.functions.F
import com.funapp.wallpaperautochangeexample.functions.gone
import com.funapp.wallpaperautochangeexample.reusables.CACHED
import kotlinx.android.synthetic.main.activity_cached.*
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.io.File
import java.util.*

class CacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cached)

        F.removeDuplicates(filesDir.listFiles().toList())
        val files = File(filesDir, CACHED).listFiles().filter {
            it.name.contains(".jpg")
        }.distinctBy {
            F.calculateMD5(it)
        }.toTypedArray()
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE)

        if (files.isNotEmpty()) {
            noCacheText.gone()
            recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            recycler.adapter = AdapterImage(files.toList())
        }
    }
}
