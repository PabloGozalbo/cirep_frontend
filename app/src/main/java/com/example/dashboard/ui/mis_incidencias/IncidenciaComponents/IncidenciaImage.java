package com.example.dashboard.ui.mis_incidencias.IncidenciaComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

public class IncidenciaImage extends androidx.appcompat.widget.AppCompatImageView {
    public IncidenciaImage(Context context) {
        super(context);
    }

    public IncidenciaImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IncidenciaImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        float height = getHeight();
        float width = getWidth();
        float trapezoidHeight = height * 0.6f; // Altura del trapecio
        float baseOffset = (width - trapezoidHeight) / 2f; // Desplazamiento de la base
        float topOffset = (width - baseOffset) / 2f; // Desplazamiento del lado superior

        // Definir la forma del trapecio con una ruta
        path.moveTo(baseOffset, height); // Esquina inferior izquierda
        path.lineTo(0, 0); // Esquina superior izquierda
        path.lineTo(width, 0); // Esquina superior derecha
        path.lineTo(width - baseOffset, height); // Esquina inferior derecha
        path.close(); // Conectar el último punto con el primer punto para cerrar la ruta

        // Recortar la imagen para que tenga la forma de trapecio
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}