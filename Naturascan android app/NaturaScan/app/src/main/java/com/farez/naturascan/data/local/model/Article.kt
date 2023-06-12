package com.farez.naturascan.data.local.model

import android.os.Parcelable
import com.farez.naturascan.R
import com.farez.naturascan.app.App
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val content: CharSequence,
    val pictureUrl: String
) : Parcelable

object sampleArticleList {
    val res = App.resourses
    val content = res.getTextArray(R.array.articleContent)
    val url = res.getStringArray(R.array.articleImageUrl)
    val title = res.getStringArray(R.array.articleTitle)
    val sampleArticle = listOf(
        Article(
            title[0],
            content[0],
            url[0]
        ),
        Article(
            title[1],
            content[1],
            url[1]
        ),
        Article(
            title[2],
            content[2],
            url[2]
        ),
        Article(
            title[3],
            content[3],
            url[3]
        ),
        Article(
            title[4],
            content[4],
            url[4]
        ),
        Article(
            title[5],
            content[5],
            url[5]
        ),
        Article(
            title[6],
            content[6],
            url[6]
        ),
    )
}

