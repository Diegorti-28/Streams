package com.mycompany.estudiantesstreams;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public class EstudiantesStreams {

    record Estudiante(String nombre, String programa, double nota) {}

    public static void main(String[] args) {

        List<Estudiante> estudiantes = List.of(
                new Estudiante("Naruto", "Arquitectura", 2.0),
                new Estudiante("Goku", "Psicologo", 2.1),
                new Estudiante("Kaneki", "Psicologo", 5.0),
                new Estudiante("Itachi", "Arquitectura", 5.0),
                new Estudiante("Obito", "Enfermero", 3.0),
                new Estudiante("Itadori", "Enfermero", 3.7),
                new Estudiante("Vegeta", "Periodista", 4.0),
                new Estudiante("Arima", "Periodista", 5.0),
                new Estudiante("Gon", "Psicologo", 4.0),
                new Estudiante("Cell", "Enfermero", 4.6)
        );

        final double NOTA_APROBATORIA = 3.0;

        //1. Filtrar estudiantes aprobados
        List<Estudiante> aprobados = estudiantes.stream()
                .filter(e -> e.nota() >= NOTA_APROBATORIA)
                .collect(Collectors.toList());

        System.out.println("1. Estudiantes aprobados:");
        aprobados.forEach(e -> System.out.println("   "+ e.nombre() + " - " + e.programa() + ": " + e.nota()));

        //2. Ordenar por nota descendente y luego por nombre
        List<Estudiante> ordenados = estudiantes.stream()
                .sorted(Comparator.comparingDouble(Estudiante::nota).reversed()
                        .thenComparing(Estudiante::nombre))
                .collect(Collectors.toList());

        System.out.println("\n2. Estudiantes ordenados (nota descendente, nombre ascendente):");
        ordenados.forEach(e -> System.out.println("   "+ e.nombre() + ": " + e.nota()));

        //3. Obtener los tres mejores
        List<Estudiante> tresMejores = estudiantes.stream()
                .sorted(Comparator.comparingDouble(Estudiante::nota).reversed()
                        .thenComparing(Estudiante::nombre))
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("\n3. Los tres mejores estudiantes:");
        tresMejores.forEach(e -> System.out.println("   "+ e.nombre() + ": " + e.nota()));

        //4. Calcular el promedio general
        double promedioGeneral = estudiantes.stream()
                .mapToDouble(Estudiante::nota)
                .average()
                .orElse(0.0);

        System.out.println("\n4. Promedio general del curso: " + promedioGeneral);

        //5. Agrupar por programa y calcular el promedio del grupo
        Map<String, Double> promedioPorPrograma = estudiantes.stream()
                .collect(Collectors.groupingBy(Estudiante::programa,
                        Collectors.averagingDouble(Estudiante::nota)));

        System.out.println("\n5. Promedio por programa:");
        promedioPorPrograma.forEach((programa, promedio) ->
                System.out.println("   " + programa + ": " + promedio));

        //6. Particionar aprobados y reprobados
        Map<Boolean, List<Estudiante>> particion = estudiantes.stream()
                .collect(Collectors.partitioningBy(e -> e.nota() >= NOTA_APROBATORIA));

        System.out.println("\n6. Particion aprobados // reprobados:");
        System.out.println("   Aprobados:");
        particion.get(true).forEach(e -> System.out.println("      "+ e.nombre() + ": " + e.nota()));
        System.out.println("   Reprobados:");
        particion.get(false).forEach(e -> System.out.println("      "+ e.nombre() + ": " + e.nota()));
    }
}
