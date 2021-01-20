package com.example.recyclerviewwithdiffutil

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class TileAdapterModel(
    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) {

    companion object {
        const val TILE_SIZE = 25
    }

    private val tiles = mutableListOf<Tile>()
    
    // tile 리스트를 초기화 하는 부분 TILE_SIZE 만큼 tiles에 넣어준다
    init {
        tiles.clear()
        (1..TILE_SIZE).forEach {
            tiles.add(Tile(it))
        }
    }

    fun size(): Int = tiles.size

    fun get(position: Int) = tiles[position]

    private fun calcDiff(newTiles: MutableList<Tile>) {
        val tileDiffUtilCallback = TileDiffUtilCallback(tiles, newTiles)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(tileDiffUtilCallback)
        diffResult.dispatchUpdatesTo(adapter) // dispatcherUpdatesTo 하면 리사이클러뷰 어댑터에 변경 결과를 넘겨준다.
    }
    
    // tiles를 newTiles로 바꾸는 함수
    private fun setNewTiles(newTiles: MutableList<Tile>) {
        tiles.clear()
        tiles.addAll(newTiles)
    }

    // tiles를 셔플하는 함수
    // DIffUtil을 사용하기 위해서는 반드시 이전 데이터셋과 변경 데이터셋을 너겨줘야 한다
    fun shuffle() {
        val newTiles = mutableListOf<Tile>()
        newTiles.addAll(tiles)
        newTiles.shuffle()

        calcDiff(newTiles)
        setNewTiles(newTiles)
    }

    fun eraseOneTile() {
        val newTiles = mutableListOf<Tile>()
        if (tiles.size >= 1) {
            val erasedRandomIndex = (Random.nextDouble() * tiles.size).toInt()
            tiles.forEachIndexed { index, tile ->
                if (index != erasedRandomIndex)
                    newTiles.add(tile)
            }
        }

        calcDiff(newTiles)
        setNewTiles(newTiles)
    }

    fun addOneTile() {
        val newTiles = mutableListOf<Tile>()
        newTiles.addAll(tiles)
        val insertRandomIndex = (Random.nextDouble() * tiles.size).toInt()
        newTiles.add(insertRandomIndex, Tile(tiles.size + 1))

        calcDiff(newTiles)
        setNewTiles(newTiles)
    }

    fun eraseThreeTile() {
        val newTiles = mutableListOf<Tile>()
        newTiles.addAll(tiles)
        if (tiles.size >= 3) {
            repeat(3) {
                val erasedRandomIndex = (Random.nextDouble() * newTiles.size).toInt()
                newTiles.removeAt(erasedRandomIndex)
            }
        }

        calcDiff(newTiles)
        setNewTiles(newTiles)
    }

    fun addThreeTile() {
        val newTiles = mutableListOf<Tile>()
        newTiles.addAll(tiles)
        repeat(3) {
            val insertRandomIndex = (Random.nextDouble() * newTiles.size).toInt()
            newTiles.add(insertRandomIndex, Tile(newTiles.size + 1))
        }

        calcDiff(newTiles)
        setNewTiles(newTiles)
    }

    inner class TileDiffUtilCallback(
        private var oldTiles: MutableList<Tile>,
        private var newTiles: MutableList<Tile>
    ) : DiffUtil.Callback() {
        // 변화하기 전의 데이터셋 사이즈
        override fun getOldListSize(): Int = oldTiles.size
        // 변화 후 데이터셋 사이즈
        override fun getNewListSize(): Int = newTiles.size

        // 두 객체가 같은 항목인지 여부를 결정
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldTiles[oldItemPosition] == newTiles[newItemPosition]
        }
        // 두 항목의 데이터가 같은지 여부를 결정, areItemsTheSame()이 true를 반환하는 경우에만 호출
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(oldItemPosition, newItemPosition)
        }

        //1. getOldListSize : 말 그대로 변화하기 전의 데이터셋의 사이즈이다
        //2. getNewListSize : 변화 후의 데이터셋 사이즈.
        //3. areItemsTheSame : 두 아이템이 같으냐? 라는 뜻이다. 아까 구현한 Tiles.equals를 이용하자.
        //4. areContentsTheSame : 두 콘텐츠가 같으냐? 라는 뜻이다. areItemTheSame이 true일 때 호출된다.
        //즉, "아이템이 같아? 그럼 콘텐츠까지 완전히 같아?"라고 물어보는 것이다. 지금과 같은 상황에서는 areItemsTheSame과 같은 의미이므로 areItemsTheSame의 결과 값을 리턴해준다
        //5. getChangePayload : 여기에 나와있지 않지만, 이 함수 또한 오버라이드할 수 있다. 이 함수는 아이템이 같은데 콘텐츠가 다를 때(즉, areItemsTheSame은 true인데 areContentsTheSame이 false일 때), 변경 내용에 대한 Payload를 가져온다. 기본적으로 null이다.
        //이 중, 5번 함수는 구현하지 않아도 된다. 1~4번은 abstract이므로 구현해야 한다.
        //보면 매우 간단하다. 단순히 Tiles.equals를 이용하고 있다.
        //그 후 이 클래스를 이용하여 위에 적힌 calcDiff함수에 나와있듯이 하면 된다.

        //요약하면 DiffUtil.CallBack을 만들고 거기에 oldList와 newList를 넘겨주고 dispatcherUpdateTo를 호출하면 된다

    }

}
