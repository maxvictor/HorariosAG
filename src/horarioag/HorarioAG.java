package horarioag;

import java.util.ArrayList;

public class HorarioAG {

    public static void main(String[] args) {
        int qtdPopi;
        ArrayList<Individuo> popi = new ArrayList<>();
        Individuo ind = new Individuo();
        String materias[] = {"Por", "Mat", "Geo", "His", "Bio", "Ing", "Fis"};

        /*
        ind.definirGrade(3, 5);
        ind.adicionarMateria(materias);
        ind.imprimirMaterias();
        ind.gerarGradeVazia();
        ind.gerarGradeAleatoria();
        ind.imprimirGrade();
        */
        
        qtdPopi = 100;
        
        // Gerar população inicial
        for(int i = 0; i < qtdPopi; i++){
            Individuo indTemp = new Individuo();
            indTemp.definirGrade(3, 5);
            indTemp.adicionarMateria(materias);
            indTemp.gerarGradeAleatoria();
            // indTemp.avaliarGrade();  falta finalizar método
            popi.add(indTemp);
        }
        
        
        
        popi.get(0).imprimirGrade();
        System.out.println(popi.get(0).verificaMateriasGrade());

        /*
        Representar o Indivíduo
        Gerar a população inicial de forma aleatória
        2. Avaliar cada indivíduo da população.
        3. Enquanto critério de parada não for satisfeito
        faça
        3.1 Selecionar os indivíduos mais aptos.
         3.2 Criar novos indivíduos aplicando os
         operadores crossover e mutação.
         3.3 Armazenar os novos indivíduos em uma
         nova população.
         3.4 Avaliar cada indivíduo da nova
         população.
       */
    }

}
