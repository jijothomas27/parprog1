val width = 32
val height = 26
val numTasks = 32
val split = width/numTasks
val columnSplits = 0 to width by split
val columnSplitRanges = columnSplits zip columnSplits.map(x => if (x+split>width) width else x+split)
columnSplitRanges.length
val rowSplits = 0 to height by numTasks+1
val rowSplitRanges = rowSplits zip rowSplits.map(y => if (y+numTasks>height) height else y+numTasks)