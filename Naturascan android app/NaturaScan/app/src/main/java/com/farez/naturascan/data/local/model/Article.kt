package com.farez.naturascan.data.local.model

import android.content.res.Resources
import android.os.Parcelable
import com.farez.naturascan.R
import com.farez.naturascan.app.App
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title : String,
    val content : String,
    val pictureUrl : String
) : Parcelable

object sampleArticleList {
    val res = App.resourses
    val content = res.getStringArray(R.array.articleContent)
    val url = res.getStringArray(R.array.articleImageUrl)
    val title = res.getStringArray(R.array.articleTitle)
    val sampleArticle = listOf(
        Article(
          title[0].toString(),
          content[0].toString(),
          url[0].toString()
        ),
        Article(
            title[1].toString(),
            content[1].toString(),
            url[1].toString()
        ),
        Article(
            title[2].toString(),
            content[2].toString(),
            url[2].toString()
        ),
        Article(
            title[3].toString(),
            content[3].toString(),
            url[3].toString()
        ),
        Article(
            title[4].toString(),
            content[4].toString(),
            url[4].toString()
        ),
        Article(
            title[5].toString(),
            content[5].toString(),
            url[5].toString()
        ),
        Article(
            title[6].toString(),
            content[6].toString(),
            url[6].toString()
        ),
    )
}

