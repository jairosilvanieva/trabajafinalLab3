package Generico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Listados <E,K,V>{
    private List<E> elementos;
    public Listados(){
        this.elementos=new ArrayList<>();
    }
    public void carga(E elemento){
        this.elementos.add(elemento);
    }
    public Integer totalDatos(){
        return elementos.size();
    }

    public E getDato(Integer pos){
        return this.elementos.get(pos);
    }
    public void cargaDesdeMap(Map<K, V> elementos){
        for (Map.Entry data : elementos.entrySet()){
            carga((E) data.getValue());
        }
    }
}