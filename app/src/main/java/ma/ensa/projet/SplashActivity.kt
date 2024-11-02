package ma.ensa.projet
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Définir un layout principal avec un fond dégradé ou une image
        val layout = ConstraintLayout(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundResource(R.drawable.bg) // Utilisez votre propre fond ou image
        }

        // Ajout d'une ImageView pour le logo
        val logoImageView = ImageView(this).apply {
            setImageResource(R.drawable.img) // Remplacez "logo" par le nom de votre image de logo
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                200.dpToPx(),
                200.dpToPx()
            )
            // Appliquer un effet de rebond au logo
            val bounceAnimation = ScaleAnimation(
                0.8f, 1f, 0.8f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 1000
                interpolator = BounceInterpolator()
                repeatCount = Animation.INFINITE
            }
            startAnimation(bounceAnimation)
        }

        // Créer un TextView stylisé pour le texte "Movies"
        val textView = TextView(this).apply {
            textSize = 50f
            setTextColor(Color.parseColor("#FFC107")) // Jaune doré
            text = ""
            typeface = android.graphics.Typeface.create("serif-monospace", android.graphics.Typeface.BOLD)
            id = View.generateViewId()

            // Appliquer une animation d’apparition/disparition
            val fadeInOut = AlphaAnimation(0.0f, 1.0f).apply {
                duration = 1000
                interpolator = LinearInterpolator()
                repeatMode = AlphaAnimation.REVERSE
                repeatCount = AlphaAnimation.INFINITE
            }
            startAnimation(fadeInOut)
        }

        // Créer une ProgressBar personnalisée
        val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            id = View.generateViewId()
            isIndeterminate = true
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16.dpToPx()
            }
            indeterminateDrawable.setTint(Color.parseColor("#FFC107"))
        }

        // Ajouter les vues au layout principal
        layout.addView(logoImageView)
        layout.addView(textView)
        layout.addView(progressBar)
        setContentView(layout)

        // Configurer les contraintes pour centrer le logo, le texte, et la ProgressBar
        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)
        constraintSet.centerHorizontally(logoImageView.id, ConstraintSet.PARENT_ID)
        constraintSet.connect(logoImageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 120)

        constraintSet.centerHorizontally(textView.id, ConstraintSet.PARENT_ID)
        constraintSet.connect(textView.id, ConstraintSet.TOP, logoImageView.id, ConstraintSet.BOTTOM, 16)

        constraintSet.connect(progressBar.id, ConstraintSet.TOP, textView.id, ConstraintSet.BOTTOM, 16)
        constraintSet.centerHorizontally(progressBar.id, ConstraintSet.PARENT_ID)
        constraintSet.applyTo(layout)

        // Affichage du texte avec effet lettre par lettre
        val textToDisplay = "Movies"
        var index = 0
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if (index < textToDisplay.length) {
                    textView.append(textToDisplay[index].toString())
                    index++
                    handler.postDelayed(this, 300) // Vitesse d'apparition
                } else {
                    handler.postDelayed({
                        layout.animate().alpha(0f).setDuration(800).withEndAction {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }
                    }, 700)
                }
            }
        }
        handler.post(runnable)
    }

    // Méthode pour convertir dp en pixels
    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}
