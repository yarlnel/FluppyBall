package ballodi.respofluppy.presentation.utils


import android.view.View

infix fun View.onclick(voidLambda: () -> Unit) {
    setOnClickListener { voidLambda.invoke() }
}

typealias VoidCallback = () -> Unit
