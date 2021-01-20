package com.example.recyclerviewwithdiffutil

import android.graphics.Color

data class Tile(
    val number: Int // 타일에 나타날 숫자
) {
    // 타일의 배경색
    val color: Int = if (number < 100) Color.BLACK + (256 * 256 * 256 * number * (100 - number) / 25) / (100)
    else Color.WHITE

    // DiffUtil은 각 아이템이 같은지, 다른지에 대한 정보를 알아야 한다.
    // 따라서 equals를 구현하여 이용
    override fun equals(other: Any?): Boolean {
        (other as? Tile)?.let {
            return this.number == it.number
        } ?: return super.equals(other)
    }

}
