package com.oguz.artbooktesting.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("arts")
data class Art(

    var name : String,
    var artistName : String,
    var year : Int,
    var imageUrl : String,

    @PrimaryKey(true)
    var id: Int? = null
)

{

}