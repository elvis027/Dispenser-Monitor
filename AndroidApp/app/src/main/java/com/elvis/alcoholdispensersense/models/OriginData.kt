package com.elvis.alcoholdispensersense.models

data class OriginData(val id: Int, val macaddr: Int, val data: String, val lat: String, val lng: String, val created_at: String, val updated_at: String) {
    constructor() : this(0,0,"","","","","")
}