package com.sgr.actorFamiliar;


public abstract class ActorFamiliar_SGR26G05 {
    private String nombre;
    private int edad;
    private enum parentesco {Suegra, Nuera, Yerno}
    private int anios_convivencia;
    private int paciencia;

    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int getAnios_convivencia() {
        return anios_convivencia;
    }
    public void setAnios_convivencia(int anios_convivencia) {
        this.anios_convivencia = anios_convivencia;
    }
    public int getPaciencia() {
        
        return paciencia;
    }
    public void setPaciencia(int paciencia) {
        if (paciencia < 0||paciencia > 100) {
            throw new IllegalArgumentException("La paciencia debe estar entre 0 y 100");
            
        }else{
            this.paciencia = paciencia;
        }
    }


}
