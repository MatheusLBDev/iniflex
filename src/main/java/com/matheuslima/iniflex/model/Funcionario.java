package com.matheuslima.iniflex.model;

import com.matheuslima.iniflex.exception.FuncionarioException;
import com.matheuslima.iniflex.util.Formatador;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.matheuslima.iniflex.util.Constantes.SALARIO_MINIMO;


public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        setSalario(salario);
        setFuncao(funcao);
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        if (salario == null || salario.compareTo(SALARIO_MINIMO) < 0) {
            throw new FuncionarioException("O salário não pode ser inferior a " + SALARIO_MINIMO);
        }
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        if (funcao == null || funcao.trim().isEmpty()) {
            throw new FuncionarioException("Função não pode estar vazia!");
        }
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return "Funcionário= " + getNome() +
                ", data de nascimento= " + Formatador.formatarData(getDataNascimento()) +
                ", salário= R$" + Formatador.formatarMoeda(salario) +
                ", cargo= " + funcao;
    }

}
