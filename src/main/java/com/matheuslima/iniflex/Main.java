package com.matheuslima.iniflex;

import com.matheuslima.iniflex.exception.FuncionarioException;
import com.matheuslima.iniflex.model.Funcionario;
import com.matheuslima.iniflex.service.FuncionarioService;
import com.matheuslima.iniflex.util.Formatador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
            funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
            funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
            funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
            funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
            funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
            funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
            funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
            funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
            funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        } catch (FuncionarioException e) {
            System.err.println("Erro ao criar funcionário: " + e.getMessage());
        }

        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        System.out.println("\n📌 Lista de Funcionários:");
        funcionarios.forEach(System.out::println);

        try {
            FuncionarioService.aumentarSalario(funcionarios, 10);
            System.out.println("\n💰 Após aumento salarial:");
            funcionarios.forEach(f ->
                    System.out.println("Cargo= " + f.getFuncao() + ", " + "Nome= " + f.getNome() + ", " + "Salário: R$" +  Formatador.formatarMoeda(f.getSalario()))
            );
        } catch (FuncionarioException e) {
            System.err.println("Erro ao aumentar salário: " + e.getMessage());
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = FuncionarioService.agruparPorFuncao(funcionarios);
        System.out.println("\n📂 Funcionários agrupados por função:");
        FuncionarioService.imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        System.out.println("\n🎂 Funcionários aniversariantes em outubro e dezembro:");
        List<Funcionario> aniversariantes = FuncionarioService.filtrarAniversariantes(funcionarios);
        if (aniversariantes.isEmpty()) {
            System.out.println("Nenhum funcionário faz aniversário nesses meses.");
        } else {
            aniversariantes.forEach(f ->
                    System.out.println(f.getNome() + ", " + Formatador.formatarData(f.getDataNascimento()))
            );
        }

        FuncionarioService.encontrarFuncionarioMaisVelho(funcionarios).ifPresentOrElse(
                maisVelho -> System.out.println("\n👴 Funcionário mais velho: " + maisVelho.getNome() +
                        ", Idade: " + (LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear())),
                () -> System.out.println("\n⚠️ Nenhum funcionário encontrado para determinar o mais velho.")
        );

        System.out.println("\n📜 Funcionários em ordem alfabética:");
        FuncionarioService.ordenarPorNome(funcionarios).forEach(f -> System.out.println("Nome= " + f.getNome()));

        BigDecimal totalSalarios = FuncionarioService.calcularTotalSalarios(funcionarios);
        System.out.println("\n💵 Total dos salários: R$" + Formatador.formatarMoeda(totalSalarios));

        System.out.println("\n📊 Quantidade de salários mínimos por funcionário:");
        Map<String, Double> salariosMinimos = FuncionarioService.calcularSalariosMinimos(funcionarios);
        if (salariosMinimos.isEmpty()) {
            System.out.println("⚠️ Nenhum salário válido encontrado.");
        } else {
            salariosMinimos.forEach((chave, qtd) ->
                    System.out.println(chave + " ganha " + qtd + " salários mínimos")
            );
        }
    }
}
