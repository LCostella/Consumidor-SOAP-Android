package com.example.leonardo.clientesoapcorreios;

/**
 * Created by Leonardo on 02/11/2015.
 */
public class Encomenda {
    private String codigo;
    private String PrazoEntrega;
    private String EntregaDomiciliar;
    private String EntregaSabado;

    Encomenda() {
    }




    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrazoEntrega() {
        return PrazoEntrega;
    }

    public void setPrazoEntrega(String prazoEntrega) {
        PrazoEntrega = prazoEntrega;
    }

    public String getEntregaDomiciliar() {
        return EntregaDomiciliar;
    }

    public void setEntregaDomiciliar(String entregaDomiciliar) {
        EntregaDomiciliar = entregaDomiciliar;
    }

    public String getEntregaSabado() {
        return EntregaSabado;
    }

    public void setEntregaSabado(String entregaSabado) {
        EntregaSabado = entregaSabado;
    }
}
