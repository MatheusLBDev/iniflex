package com.matheuslima.iniflex.service;

import com.matheuslima.iniflex.exception.FuncionarioException;
import com.matheuslima.iniflex.model.Funcionario;
import com.matheuslima.iniflex.model.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.matheuslima.iniflex.util.Constantes.SALARIO_MINIMO;

public class FuncionarioService {

    public static void aumentarSalario(List<Funcionario> funcionarios, double percentual) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        if (percentual <= 0) {
            throw new FuncionarioException("Erro: Percentual de aumento inválido.");
        }

        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(BigDecimal.valueOf(1 + percentual / 100)))
        );
    }

    public static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    public static List<Funcionario> filtrarAniversariantes(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            return Collections.emptyList();
        }

        return funcionarios.stream()
                .filter(f -> EnumSet.of(Month.OCTOBER, Month.DECEMBER).contains(f.getDataNascimento().getMonth()))
                .collect(Collectors.toList());
    }

    public static Optional<Funcionario> encontrarFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        return funcionarios.stream()
                .min(Comparator.comparing(Pessoa::getDataNascimento));
    }

    public static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Map<String, Double> calcularSalariosMinimos(List<Funcionario> funcionarios) {
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new FuncionarioException("Erro: Lista de funcionários vazia ou nula.");
        }

        if (SALARIO_MINIMO.compareTo(new BigDecimal("1212")) < 0) {
            throw new FuncionarioException("Erro: O salário mínimo deve ser maior ou igual a 1212.");
        }

        return funcionarios.stream()
                .collect(Collectors.toMap(
                        f -> f.getFuncao() + ": " + f.getNome(),
                        f -> f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP).doubleValue(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        if (funcionariosPorFuncao == null || funcionariosPorFuncao.isEmpty()) {
            throw new FuncionarioException("Erro: Nenhum funcionário para agrupar.");
        }

        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });
    }
}





