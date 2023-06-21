package Models.Contenedora;

import java.io.Serializable;


   public class Insumos implements Serializable {
       private int vacuna;
       private int guantes;
       private int suero;
       private int isabelino;
       private int gasas;

       public Insumos() {
           this.vacuna = 10;
           this.guantes = 10;
           this.suero = 10;
           this.isabelino = 10;
           this.gasas = 10;
       }

       public boolean isVacunaDisponible() {
           return this.vacuna > 0;
       }

       public void usarVacuna() throws Exception {
           if (!isVacunaDisponible()) {
               throw new Exception("No hay vacunas disponibles.");
           }
           this.vacuna--;
       }

       public boolean isMaterialCastracionDisponible() {
           return this.guantes > 0 && this.suero > 0 && this.isabelino > 0 && this.gasas > 0;
       }

       public void usarMaterialCastracion() throws Exception {
           if (!isMaterialCastracionDisponible()) {
               throw new Exception("No hay suficientes materiales para castrar.");
           }
           this.guantes--;
           this.suero--;
           this.isabelino--;
           this.gasas--;
       }

       public boolean isGuantesDisponibles() {
           return this.guantes > 0;
       }

       public void usarGuantes() throws Exception {
           if (!isGuantesDisponibles()) {
               throw new Exception("No hay guantes disponibles.");
           }
           this.guantes--;
       }


   }

