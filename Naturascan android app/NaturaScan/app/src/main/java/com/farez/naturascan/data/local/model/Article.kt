package com.farez.naturascan.data.local.model

data class Article(
    val title : String,
    val content : String,
    val pictureUrl : String
)

object sampleArticleList {
    val asd = "Sampel text yang digunakan untuk mengisi deskripsi asrtikel pada home page"
    val sampleArticle = listOf(
        Article(
          "Tumbuhan Kecil yang Memiliki Banyak Manfaat",
          asd,
          "https://indihome.co.id/uploads/images/blog/Ternyata-Begini-Cara-Tumbuhan-Menyesuaikan_22872221117092459_D.jpg"
        ),
        Article(
            "Manfaat Bunga Matahari",
            asd,
            "https://asset-a.grid.id/crop/0x0:0x0/750x504/photo/2022/06/23/fc56fac8-1e52-45c4-a1d9-b57c0cac-20220623112609.jpeg"
        ),
        Article(
            "Tumbuhan Terkecil di Dunia",
            asd,
            "https://cdn0-production-images-kly.akamaized.net/Tddj9DuYHjIXUFfTatF4XR8TbIo=/680x383/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/842400/original/040901200_1427968460-green_nature_plant_macro_seed_hd_wallpaper.jpg"
        ),
        Article(
            "10 Tumbuhan Karnivora yang Ada di Indonesia",
            asd,
            "https://cdn0-production-assets-kly.akamaized.net/medias/842299/big/078735800_1427963822-Tumbuhan-Langka-di-Dunia---Tanaman-Karnivora.jpg"
        ),
        Article(
            "10 Pohon dengan Bentuk Seperti Jamur",
            asd,
            "https://cdn0-production-assets-kly.akamaized.net/medias/842301/big/078966500_1427963822-dragon-trees-socotra.jpg"
        ),
        Article(
            "Pohon-pohon Terbesar di Dunia",
            asd,
            "https://cdn0-production-assets-kly.akamaized.net/medias/842304/big/079280000_1427963822-Tumbuhan-Langka-di-Dunia---Pohon-Botol.jpg"
        ),
        Article(
            "Manfaat Ganja dalam Bidang Medis",
            asd,
            "https://cdn1-production-assets-kly.akamaized.net/medias/842308/big/079777400_1427963822-Tumbuhan-langka-Tanaman-Menari-Desmodium-Gyrans.jpg"
        ),
    )
}

