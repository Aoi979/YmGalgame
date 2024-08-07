package com.nullable.ymgalgame.ui.theme

private const val SlideOffsetDenominator = 10
private const val SlideOffsetFraction = SlideOffsetDenominator - 1

val Int.slideOffset: Int
    get() = this * SlideOffsetFraction / SlideOffsetDenominator

const val Scaling = .9F

const val FadingAlpha = .9F