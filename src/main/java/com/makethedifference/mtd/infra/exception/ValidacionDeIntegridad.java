package com.makethedifference.mtd.infra.exception;

/**
 * Excepción personalizada para errores de validación de integridad.
 * Esta excepción extiende RuntimeException y se utiliza para indicar
 * violaciones de integridad en la aplicación.
 */
public class ValidacionDeIntegridad extends RuntimeException {
    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param s El mensaje de error que describe la violación de integridad.
     */
    public ValidacionDeIntegridad(String s) {
        super(s);   // Llama al constructor de RuntimeException con el mensaje de error
    }
}
