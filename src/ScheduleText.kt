var continuousArray: ArrayList<Int> = arrayListOf<Int>()
val duplicateLimit: Int = 2 //允許最多重覆次數
val dileLimit: Int = 2 //閒置最多次數
val showLog: Boolean = false //是否顯示debug 訊息

fun main(args: Array<String>) {
//	println("HelloWorld")
	val totalCount: Int = 12 //執行次數
	val totalPeople: Int = 8 //最少人數 為 6，不可低於6人
	val endCount: Int = 4 //雙打 2邊 為 4人

	var tmpRandom: Int
	var tmpSet: HashSet<Int>
	var count: Int  //計算單次人數
	var singleRecord: String
	var isFirstCycle: Boolean = true
	var firstCycle: Int = 0
	var isRunning: Boolean = true

//	var totalGen: ArrayList<Int> = arrayListOf<Int>()
	var gameList: ArrayList<String> = arrayListOf<String>()
	var totalGen: IntArray = IntArray(totalPeople + 1)
	var idleList: ArrayList<String> = arrayListOf<String>()
//	var gameList: Array<String?> = arrayOfNulls<String>(totalPeople + 1)


//	println("gameList.size = " + gameList.size);
	for (i in 1..totalPeople + 1) {
		//totalGen.add(0)
		//	totalGen[i] = 0
		continuousArray.add(0)
	}


	for (i in 1..totalCount) {
//	while(isRunning){
		singleRecord = ""
		tmpSet = HashSet<Int>() //重新下一組

		count = 0 //計算單次人數

		while (count < endCount) {
			//設定一開始先從 1 2 3 4 5 6 7 8 開始輪
			if (isFirstCycle) {
				firstCycle++
				tmpRandom = firstCycle
				if (firstCycle >= totalPeople) {
					isFirstCycle = false
				}
			} else {
				if (idleList.size > 0) {
					var tmp = (Math.random() * idleList.size).toInt();
if (showLog) {
//	println(idleList.size);					
//	println("idleList.size : + $idleList.size");					
	println("tmp : + $tmp");
	println("idleList 1: + $idleList");
}
					tmpRandom = idleList.get(tmp).toInt()
					idleList.removeAt(tmp)
if (showLog) {
	println("idleList 2: + $idleList");
}

				} else {
					tmpRandom = (Math.random() * totalPeople).toInt() + 1 //產生亂數
				}
			}

			if (!overLimit(gameList, tmpRandom)) {
				if (tmpSet.add(tmpRandom)) {//亂數是否已存在這一場
					//	println(tmpSet.toString())

					count++
					
					if (count % 2 == 0) {
						if (count == 2 && endCount / 2 != 1) { //
							singleRecord += tmpRandom.toString() + "   VS   "
						} else {
							singleRecord += tmpRandom.toString()
						}
					} else {
						if (endCount / 2 == 1) {
							singleRecord += tmpRandom.toString() + "   VS   "
						} else {
							singleRecord += tmpRandom.toString() + " - "
						}
					}
					//統計號碼出現幾次
					totalGen.set(tmpRandom, totalGen[tmpRandom] + 1)
				}


				if (tmpSet.size >= totalPeople) {
					isRunning = false
//println("~~~~ running Finish ~~~~")
				} else {
//					println(count)
				}
			}
		}

		println(i.toString() + ": " + singleRecord)
//		println(singleRecord)
		gameList.add(singleRecord)

		//閒置清單
		idleList = getIdleList(gameList, totalPeople)

//		println("gameList 2 size = "+ gameList.size);
	}
	println()
//	println(totalGen)
	for ((i, v) in totalGen.withIndex()) {
		if (i != 0) {
			print("[$i 號]:$v 次 ")
		}
	}
}

fun overLimit(tmpRandom: Int): Boolean {
	var result = false

	continuousArray.set(tmpRandom, continuousArray[tmpRandom] + 1)
//	println(continuousArray.toString())
	if (continuousArray.get(tmpRandom) >= duplicateLimit) {
//		println(tmpRandom.toString() + " 號出現 " + continuousArray.get(tmpRandom).toString()+ " 次")
		continuousArray.set(tmpRandom, 0)
		result = true;
	}
	return result
}

/**
 *判斷是否有超過連續2次
 */
fun overLimit(gameList: ArrayList<String>, tmpRandom: Int): Boolean {
	var result: Boolean = false
	var duplicateCount: Int = 0

	if (gameList.size >= duplicateLimit) {
		for (i in 1..duplicateLimit) {
			//println(gameList.get(gameList.size - i))
			if (gameList[gameList.size - i].indexOf(tmpRandom.toString()) != -1) {
//				print( "\t數字：" + tmpRandom + "\t編號：" + i.toString() + "\t" + gameList[gameList.size - i])
				duplicateCount++
			}
		}
//		println()
	}

	if (duplicateCount >= duplicateLimit) {
		//println("數字 " + tmpRandom.toString() + " 重複 " + duplicateCount + " 次")
		result = true
	}

	return result
}

/**
 * 產生閒置次數
 */
fun getIdleList(gameList: ArrayList<String>, totalPeople: Int): ArrayList<String> {
	var tmpArrayList: ArrayList<String> = arrayListOf<String>()
	var count: Int
	if (gameList.size > dileLimit) {
		for (i in 1..totalPeople) {
			count = 0
			for (j in 1..dileLimit) {
				if (gameList[gameList.size - j].indexOf(i.toString()) == -1) {
					count++
				}
			}
			if (count == dileLimit) {
				tmpArrayList.add(i.toString())
			}
		}
	}
if (showLog) {
	println("\t $tmpArrayList")
}
	return tmpArrayList
}

