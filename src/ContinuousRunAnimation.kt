class ContinuousRunAnimation(val id: String, val params: Map<*, *>) {

    private var continueAnimation = true

    init {
        sendAnimation()
//        println(id)
    }

    fun startAnimation() {
        val (animation,
                color1,
                color2,
                color3,
                color4,
                color5,
                colorList,
                direction,
                spacing,
                delay) = params
        when (animation) {
            Animations.ALTERNATE ->
                alternate(color1, color2)
            Animations.MULTIPIXELRUN ->
                multiPixelRun(color1, spacing, direction, delay)
            Animations.PIXELMARATHON ->
                pixelMarathon(color1, color2, color3, color4, color5)
            Animations.PIXELRUN ->
                pixelRun(color1, color2, direction, delay)
            Animations.PIXELRUNWITHTRAIL ->
                pixelRunWithTrail(color1, color2, direction, delay)
            Animations.SMOOTHCHASE ->
                smoothChase(colorList, direction, delay)
            Animations.SPARKLE ->
                sparkle(color1, delay)
            Animations.STACKOVERFLOW ->
                stackOverflow(color1, color2)
        }
    }

    fun endAnimation() {
        println("Animation $id ending")
        continueAnimation = false
    }

    fun sendAnimation() {
        GUISocket.sendAnimation(params, id)
    }


    private fun alternate(color1: Long, color2: Long?) = try {
        val c2 = color2 ?: 0x0
        while (continueAnimation) leds.alternate(ColorContainer(color1), ColorContainer(c2))
    } catch (e: Exception) {
        println("Handler Error - Alternate: $e")
    }

    private fun multiPixelRun(color: Long, spacing: Int?, direction: Char?, delay: Int?) = try {
        val s = spacing ?: 4
        val d = delay ?: 150
        while (continueAnimation) {
            leds.multiPixelRun(
                s,
                when (direction?.toUpperCase()) {
                    'F' -> Direction.FORWARD
                    'B' -> Direction.BACKWARD
                    else -> Direction.FORWARD
                },
                ColorContainer(color),
                delay = d
            )
        }
    } catch (e: Exception) {
        println("Handler Error - Multi-Pixel Run Animation: $e")
    }

    private fun pixelMarathon(color1: Long, color2: Long?, color3: Long?, color4: Long?, color5: Long?) = try {
        val c2 = color2 ?: CCGreen.hex
        val c3 = color3 ?: CCYellow.hex
        val c4 = color4 ?: CCBlue.hex
        val c5 = color5 ?: CCPurple.hex
        while (continueAnimation) {
            leds.pixelMarathon(
                ColorContainer(color1),
                ColorContainer(c2),
                ColorContainer(c3),
                ColorContainer(c4),
                ColorContainer(c5)
            )
        }
    } catch (e: Exception) {
        println("Handler Error - Pixel Marathon Animation: $e")
    }

    private fun pixelRun(color1: Long, color2: Long?, direction: Char?, delay: Int?) = try {
        val d = delay ?: 50
        val c2 = color2 ?: 0x0
        while (continueAnimation) {
            leds.pixelRun(
                when (direction?.toUpperCase()) {
                    'F' -> Direction.FORWARD
                    'B' -> Direction.BACKWARD
                    else -> Direction.FORWARD
                },
                ColorContainer(color1),
                ColorContainer(c2),
                d
            )
        }
    } catch (e: Exception) {
        println("Handler Error - Pixel Run Animation: $e")
    }

    private fun pixelRunWithTrail(color1: Long, color2: Long?, direction: Char?, delay: Int?) = try {
        val d = delay ?: 50
        val c2 = color2 ?: 0x0
        while (continueAnimation) {
            leds.pixelRunWithTrail(
                when (direction?.toUpperCase()) {
                    'F' -> Direction.FORWARD
                    'B' -> Direction.BACKWARD
                    else -> Direction.FORWARD
                },
                ColorContainer(color1),
                ColorContainer(c2),
                delay = d
            )
        }
    } catch (e: Exception) {
        println("Handler Error - Pixel Run With Trail Animation: $e")
    }

    private fun smoothChase(colorList: List<*>?, direction: Char?, delay: Int?) = try {
        val d = delay ?: 50
        val cList = mutableListOf<ColorContainer>()
        colorList?.forEach { c -> cList.add(ColorContainer(c as Long)) }
        println("$colorList = $cList")
        while (continueAnimation) {
            leds.smoothChase(
                cList,
                when (direction?.toUpperCase()) {
                    'F' -> Direction.FORWARD
                    'B' -> Direction.BACKWARD
                    else -> Direction.FORWARD
                },
                delay = d
            )
        }
    } catch (e: Exception) {
        println("Handler Error - Smooth Chase Animation: $e")
    }

    private fun sparkle(color: Long, delay: Int?) = try {
        val d = delay ?: 10
        while (continueAnimation) leds.sparkle(ColorContainer(color), delay = d)
    } catch (e: Exception) {
        println("Handler Error - Sparkle to Color Animation: $e")
    }

    private fun stackOverflow(color1: Long, color2: Long?) = try {
        val c2 = color2 ?: 0xFF
        while (continueAnimation) leds.stackOverflow(ColorContainer(color1), ColorContainer(c2))
    } catch (e: Exception) {
        println("Handler Error - Stack Overflow Animation: $e")
    }

}
