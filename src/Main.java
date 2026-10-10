import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import model.Ganho;
import model.Usuario;

public class Main {
  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    System.out.println("Qual seu nome?");
    String nome = scanner.nextLine();
    System.out.println("Qual seu email?");
    String email = scanner.nextLine();
    System.out.println("Qual sua senha?");
    String senha = scanner.nextLine();
    int id = Math.floorMod(UUID.randomUUID().hashCode(), Integer.MAX_VALUE) + 1;

    Usuario user = new Usuario(nome, email, senha, id);

    System.out.println("ID: " + user.getId());
    System.out.println("Nome: " + user.getNome());
    System.out.println("Email: " + user.getEmail());
    System.out.println("Senha: " + user.getSenha());

    ArrayList<Ganho> ganhosList = new ArrayList<>();
    File arquivoGanhos = new File("ganhos.txt");
    int idGanho = 0;
    if (arquivoGanhos.exists()) {
      try (Scanner leitor = new Scanner(arquivoGanhos)) {
        while (leitor.hasNextLine()) {
          String linha = leitor.nextLine().trim();
          if (linha.isBlank()) {
            continue;
          }
       
          String[] partes = linha.split(";", -1);
          if (partes.length != 4) {
            continue;
        
          }
          try {
            double valorSalvo = Double.parseDouble(partes[0].trim());
            String plataformaSalva = partes[1].trim();
            LocalDate dataSalva = LocalDate.parse(partes[2].trim());
            int idSalvo = Integer.parseInt(partes[3].trim());
             if (idSalvo > idGanho) {
            idGanho = idSalvo;
          }
            ganhosList.add(new Ganho(idSalvo, valorSalvo, plataformaSalva, dataSalva));
          } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
            // Ignora linhas inválidas do arquivo e continua lendo os demais registros.
          }
        }
      }
    }
    int opcao;
    do {
      System.out.println("1 - Cadastrar ganho");
      System.out.println("2 - Sair");
    System.out.print("3- Lista de ganhos:\n");
    System.out.print("4- Remover ganho:\n");
    System.out.print("5- Filtro por data:\n");

    
      opcao = scanner.nextInt();
      scanner.nextLine(); // limpa a sobra do Enter

      switch (opcao) {
        case 1:
          System.out.println("Diga o ganho do dia");
          double valor = scanner.nextDouble();
          scanner.nextLine();
          System.out.println("Qual plataforma?");
          String plataforma = scanner.nextLine();

          idGanho++;
          Ganho novoGanho = new Ganho(idGanho, valor, plataforma, LocalDate.now());
          ganhosList.add(novoGanho);

          try (FileWriter filewriter = new FileWriter("ganhos.txt", true)) {
            filewriter.write(novoGanho.getValor() + ";" + novoGanho.getPlataforma() + ";"
                + novoGanho.getData() + ";" + novoGanho.getId() + "\n");
          }
          break;
        case 2:
          System.out.println("Saindo do programa...");
          break;
           case 3:
          System.out.println("Lista de ganhos:");
          for (Ganho ganho : ganhosList) {
            System.out.println("ID: " + ganho.getId() + ", Valor: " + ganho.getValor() + ", Plataforma: "
                + ganho.getPlataforma() + ", Data: " + ganho.getData());
          }
          break;
          case 4:
          System.out.println("Digite o ID do ganho que deseja remover:");
          int idRemover = scanner.nextInt();
          scanner.nextLine(); // limpa a sobra do Enter
          if(ganhosList.removeIf(ganho -> ganho.getId() == idRemover)) {
            System.out.println("Ganho removido com sucesso.");
            // Reescreve o arquivo com os ganhos restantes
            try (FileWriter filewriter = new FileWriter("ganhos.txt")) {
              for (Ganho ganho : ganhosList) {
                filewriter.write(ganho.getValor() + ";" + ganho.getPlataforma() + ";"
                    + ganho.getData() + ";" + ganho.getId() + "\n");
              }
            }
          } else {
            System.out.println("Ganho com ID " + idRemover + " não encontrado.");
          }
          break;
          case 5:
          System.out.println("Digite a data inicial (YYYY-MM-DD):");
          String dataInicialStr = scanner.nextLine();
          System.out.println("Digite a data final (YYYY-MM-DD):");
          String dataFinalStr = scanner.nextLine();
          try {
            double TotalPeriodo = 0;
            boolean encontrouGanhos = false;
            LocalDate dataInicial = LocalDate.parse(dataInicialStr);
            LocalDate dataFinal = LocalDate.parse(dataFinalStr);
            System.out.println("Ganhos entre " + dataInicial + " e " + dataFinal + ":");
           for (Ganho ganho : ganhosList) {
              if (!ganho.getData().isBefore(dataInicial) && !ganho.getData().isAfter(dataFinal)) {
                System.out.println("ID: " + ganho.getId() + ", Valor: " + ganho.getValor() + ", Plataforma: "
                    + ganho.getPlataforma() + ", Data: " + ganho.getData());
                TotalPeriodo += ganho.getValor();
                encontrouGanhos = true; }
            
              }
              if (!encontrouGanhos) {
                System.out.println("Nenhum ganho encontrado nesse período.");
              }
              else {
                System.out.println("Total do período: " + TotalPeriodo);
              }
            
           
            }
            catch (java.time.format.DateTimeParseException e) {
              System.out.println("Formato de data inválido. Por favor, use o format YYYY-MM-DD.");
              
            }
             
          break;
        default:
          System.out.println("Opção inválida");
         
      }
    } while (opcao != 2);

    double totalifood = 0;
    double total99 = 0;
    for (Ganho ganho : ganhosList) {
      if (ganho.getPlataforma().equalsIgnoreCase("iFood")) {
        totalifood += ganho.getValor();
      } else {
        total99 += ganho.getValor();
      }
    }

    System.out.println("Total iFood: " + totalifood);
    System.out.println("Total 99: " + total99);
    System.out.println("Total: " + (totalifood + total99));
  }
}
