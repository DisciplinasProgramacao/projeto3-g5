import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
     // Instâncias estáticas de Estacionamento
    public static Estacionamento estacionamento1 = new Estacionamento("Estacionamento 1", 12, 22);
    public static Estacionamento estacionamento2 = new Estacionamento("Estacionamento 2", 10, 28);
    public static Estacionamento estacionamento3 = new Estacionamento("Estacionamento 3", 15, 25);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /*  Função principal que controla o fluxo do programa.
         Inicializa os estacionamentos e apresenta um menu principal para o usuário.
         Permite a entrada como cliente, gestor ou a saída do programa.*/

        // estacionamento1.carregarArquivo();
        // estacionamento2.carregarArquivo();
        // estacionamento3.carregarArquivo();
        
        // Inicialização dos estacionamentos e tentativa de carregar estados anteriores
        int escolha;
        do {
            // Carregar estacionamentos previamente salvos
            estacionamento1 = new Estacionamento("Estacionamento 1", 12, 22);
            estacionamento2 = new Estacionamento("Estacionamento 2", 10, 28);
            estacionamento3 = new Estacionamento("Estacionamento 3", 15, 25);
            // if (estacionamento1.getId()[0] != null)
            try {
                estacionamento1 = estacionamento1.carregarEstado();
                estacionamento2 = estacionamento2.carregarEstado();
                estacionamento3 = estacionamento3.carregarEstado();
                System.out.println("carregando estacionamento salvo ");
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
                System.out.println("erro ao carregar estacionamentos");
            }
            // estacionamento2.carregarArquivo();
            // estacionamento3.carregarArquivo();
            
            // Menu principal
            System.out.println("Menu Principal");
            System.out.println("1. Entrar como Cliente");
            System.out.println("2. Entrar como Gestor");
            System.out.println("3. Sair");

            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            // Switch para lidar com as opções do menu principal
            switch (escolha) {
                case 1:
                    entrarComoCliente();
                    break;
                case 2:
                    entrarComoGestor();
                    break;
                case 3:
                    System.out.println("Saindo do programa.");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        } while (escolha < 4 && escolha > 0);
    }
    


    /* Método para entrada como cliente
     Permite ao usuário escolher um estacionamento e entrar como cliente.
     Chama o menu do cliente para realizar operações relacionadas ao cliente.
    */
    public static void entrarComoCliente() {
        Scanner scanner = new Scanner(System.in);
        Estacionamento e = estacionamento1;
        System.out.println("escolha o estacionamento: ");
        int escolha = scanner.nextInt();

        switch (escolha) {
            case 1:
                e = estacionamento1;
                break;
            case 2:
                e = estacionamento2;
                break;
            case 3:
                e = estacionamento3;
                break;
            default:
                System.out.println("Opção inválida. Escolha uma opção válida.");

                break;
        }

        // System.out.println("nome cliente"+ e.getId()[0].getNome()+" placa do
        // carro"+e.getId()[0].getVeiculos()[0].getPlaca());

        scanner.nextLine();
        System.out.println("Indique seu identificador: ");
        String id = scanner.nextLine();

        menuCliente(e, id);
    }
    


    /*  Método para o menu do cliente
      Apresenta um menu para o cliente realizar diversas operações no estacionamento.
      Inclui estacionar veículo, sair com veículo, acessar histórico, adicionar veículo, ser mensalista e adicionar serviço.  */
       
    public static void menuCliente(Estacionamento estacionamento, String id) {
        Scanner scanner = new Scanner(System.in);
        Cliente[] arrCliente = estacionamento.getId();
        Cliente c = estacionamento.getId()[0];
        boolean existe = false;
        if (arrCliente.length > 0) {
            for (Cliente cliente : arrCliente) {
                if (cliente != null && cliente.getId().equals(id)) {
                    c = cliente;
                    existe = true;
                }
            }
        }
        if (!existe) {
            System.out.println("Indique seu nome: ");
            String nome = scanner.nextLine();
            TipoCliente tipoCliente = null;
            c = new Cliente(nome, id,tipoCliente.HORISTA , null);
            estacionamento.addCliente(c);
        }

        // Scanner scanner = new Scanner(System.in);
        int escolha;
        do {
            System.out.println("Menu Cliente");
            System.out.println("1. Estacionar Veiculo");
            System.out.println("2. Sair com veículo");
            System.out.println("3. Acessar histórico de uso do estacionamento");
            System.out.println("4. adicionar veículo");
            System.out.println("5. Seja um mensalista!");
            System.out.println("6. Adicionar um serviço");
            System.out.println("7. Sair");
            escolha = scanner.nextInt();
            scanner.nextLine();
            switch (escolha) {
                case 1:
                    estacionarVeiculo(estacionamento);
                    break;
                case 2:
                    sairVeiculo(estacionamento);
                    break;
                case 3:
                    ArrayList<String> historico = estacionamento.historicoDeUso(c);
                    System.out.println(historico);
                    break;
                case 4:
                    System.out.println("digite a placa do carro.");
                    String placa = scanner.nextLine();
                    adicionarVeiculo(estacionamento, id, placa);
                    break;
                case 5:
                    menuMensalista(estacionamento, id);
                    menuCliente(estacionamento, id);
                    break;
                    case 6:
                    menuServico(estacionamento, id);
                    menuCliente(estacionamento, id);
                    break;
                case 7:
                    System.out.println("Saindo do menu do cliente.");
                    // System.out.println("contcli_2 " + estacionamento1.contCli);
                    salvarEstacionamentos();

                    break;
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        } while (escolha < 7 && escolha > 0);
    }

    /*  Método para o menu de tornar-se mensalista
        Permite ao cliente tornar-se mensalista, escolhendo entre opções com ou sem turno definido.
    */
    public static void menuMensalista(Estacionamento estacionamento, String id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1- Ser Mensalista por turno");
        System.out.println("2- Ser Mensalista sem turno definido");
        System.out.println("3- Sair");
        int escolha = scanner.nextInt();
        TipoCliente tipoCliente = null;
        scanner.nextLine();
        switch (escolha) {
            case 1:
                System.out.println("Escolha turno: ");
                System.out.println("1- Manhã");
                System.out.println("2- Tarde");
                System.out.println("3- Noite");
                int turno = scanner.nextInt();
                scanner.nextLine();
                switch (turno) {
                    case 1:
                        estacionamento.setMensalista(tipoCliente.TURNO_MANHA, id);
                        System.out.println("Mensalidade de 200 reais adicionada");  
                        break;
                    case 2:
                        estacionamento.setMensalista(tipoCliente.TURNO_TARDE, id);
                        System.out.println("Mensalidade de 200 reais adicionada");
                        break;
                    case 3:
                        estacionamento.setMensalista(tipoCliente.TURNO_NOITE, id);
                        System.out.println("Mensalidade de 200 reais adicionada");
                        break;
                    default:
                        System.out.println("Opção inválida. Voltando para menu principal...");
                        break;
                }
            break;
            case 2: 
                estacionamento.setMensalista(tipoCliente.MENSALISTA, id);
                System.out.println("Mensalidade de 500 reais adicionada");               
                break;
            case 3:
                System.out.println("Saindo...");
                return;
            default:
                System.out.println("Opção inválida. Escolha uma opção válida.");
                break;
        }
    }


    /*  Método para o menu de adição de serviços
        Permite ao cliente adicionar serviços ao seu veículo, como manobrista, lavagem e polimento.
    */
    public static void menuServico(Estacionamento estacionamento, String id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();

        System.out.println("1- Manobrista");
        System.out.println("2- Lavagem");
        System.out.println("3- Polimento(inclui lavagem)");
        int escolha = scanner.nextInt();
        estacionamento.adicionarServico(id,escolha,plaque);
    }


    /* Método para salvar os estados dos estacionamentos
       Salva os estados dos estacionamentos em arquivos para persistência de dados.
    */
    public static void salvarEstacionamentos() {
        try {
            estacionamento1.salvarEstado();
            estacionamento2.salvarEstado();
            estacionamento3.salvarEstado();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* Método para adicionar veículo ao cliente
       Adiciona um novo veículo ao cliente no estacionamento.
    */
    public static void adicionarVeiculo(Estacionamento estacionamento, String id, String placa) {
        Veiculo v = new Veiculo(placa, 20);
        estacionamento.addVeiculo(v, id);
        menuCliente(estacionamento, id);
    }
    /*Método para estacionar um veículo
      Permite ao cliente estacionar um veículo, solicitando a placa e a hora de entrada.
    */
    public static void estacionarVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        System.out.println("Indique hora de entrada: ");
        String hour = scanner.nextLine();
        try {
            LocalDateTime momentoAtual = LocalDate.now().atTime(Integer.parseInt(hour.split(":")[0]),
                Integer.parseInt(hour.split(":")[1]));
            estacionamento.estacionar(plaque, momentoAtual);
        } catch (Exception e) {
            System.out.println("digite um horario valido");
        }

    }
    /* Método para sair com um veículo
       Permite ao cliente sair com um veículo, solicitando a placa e a hora de saída.
     */
    public static void sairVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        System.out.println("Indique hora de saida: ");
        String hour = scanner.nextLine();
        try {
        LocalDateTime momentoAtual = LocalDate.now().atTime(Integer.parseInt(hour.split(":")[0]),
                Integer.parseInt(hour.split(":")[1]));
        if(!estacionamento.sair(plaque, momentoAtual))
        sairVeiculo( estacionamento);
        } catch (Exception e) {
            System.out.println("digite um horario valido");
        }
    }
    /* Método para entrada como gestor
      Permite ao usuário entrar como gestor e realizar operações de gestão no estacionamento escolhido.
    */
    public static void entrarComoGestor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual estacionamento você quer gerenciar(1 a 3)?");
        int num = scanner.nextInt();
        Estacionamento estacionamento = new Estacionamento(null, num, num);

        if (num == 1) {
            estacionamento = estacionamento1;
        }
        if (num == 2) {
            estacionamento = estacionamento2;
        }
        if (num == 3) {
            estacionamento = estacionamento3;
        }

        int escolha;
        do {
            System.out.println("Menu Gestor");
            System.out.println("1. Ver valor total arrecadado");
            System.out.println("2. Ver valor arrecadado em mês específico");
            System.out.println("3. Ver valor médio de cada utilização do estacionamento");
            System.out.println("4. Gerar ranking dos clientes que mais geraram arrecadação em um determinado mês");
            System.out.println("5. Sair");
            escolha = scanner.nextInt();
            int mes;
            switch (escolha) {
                case 1:
                    System.out.println(estacionamento.totalArrecadado());
                    break;
                case 2:
                    System.out.println("Insira o mês desejado(1 a 12)");
                    mes = scanner.nextInt();
                    System.out.println(estacionamento.arrecadacaoNoMes(mes));
                    break;
                case 3:
                    System.out.println(estacionamento.valorMedioPorUso());
                    break;
                case 4:
                    System.out.println("Insira o mês desejado(1 a 12)");
                    mes = scanner.nextInt();
                    System.out.println(estacionamento.top5Clientes(mes));
                    break;
                case 5:
                    System.out.println("Saindo do gestor.");
                    break;
                    //System.exit(0);
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        } while (escolha < 5 && escolha > 0);
    }
}
