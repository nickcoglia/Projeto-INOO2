package br.edu.ifsp.arq.tsi.inoo.locacao.view;

import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

import br.edu.ifsp.arq.tsi.inoo.locacao.controller.LocacaoController;
import br.edu.ifsp.arq.tsi.inoo.locacao.controller.PessoaController;
import br.edu.ifsp.arq.tsi.inoo.locacao.controller.CarrosController;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Carro;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Locacao;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Pessoa;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaFisica;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaJuridica;

public class App {
    private static int indexCarro;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        ArrayList<PessoaFisica> pessoasFisicasLocacao = new ArrayList<>();
        ArrayList<PessoaJuridica> pessoasJuridicasLocacao = new ArrayList<>();
        ArrayList<Carro> carrosLocacao = new ArrayList<>();
        ArrayList<Locacao> locacoes = new ArrayList<>();

        while (continuar) {
            System.out.println("\nBem-vindo! O que você deseja fazer?");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Remover cliente");
            System.out.println("3. Pesquisar cliente");
            System.out.println("4. Cadastrar carro");
            System.out.println("5. Remover carro");
            System.out.println("6. Pesquisar carro");
            System.out.println("7. Realizar Locação");
            System.out.println("8. Pesquisar Locação");
            System.out.println("9. Realizar Devolução");
            System.out.println("10. Gerar Relatório");
            System.out.println("0. Sair do Programa");

            System.out.println("\nEscolha uma opção:");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    cadastrarCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 2:
                    removerCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 3:
                    pesquisarCliente(pessoasFisicasLocacao, pessoasJuridicasLocacao, scanner);
                    break;
                case 4:
                    cadastrarCarro(carrosLocacao, scanner);
                    break;
                case 5:
                    removerCarro(carrosLocacao, scanner);
                    break;
                case 6:
                    pesquisarCarro(carrosLocacao, scanner);
                    break;
                case 7:
                    realizarLocacao(pessoasFisicasLocacao, pessoasJuridicasLocacao, carrosLocacao, locacoes, scanner);
                    break;

                case 8:
                    pesquisarLocacao(locacoes, scanner);
                    break;

                case 9:
                    realizarDevolucao(scanner, locacoes);
                    break;
                case 10:
                    exibirRelatorio(locacoes);
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida!");
                    break;
            }
        }
        scanner.close();
    }

    public static void cadastrarCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {
        int opcao;
        System.out.println(" 1 - Físico  2 - Jurídico : \n");
        opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (opcao == 1) {
            boolean cpfDuplicado = true;
            while (cpfDuplicado) {
                System.out.println("\nDigite o nome da Pessoa Física:");
                String nomePF = scanner.nextLine();
                System.out.println("Digite o CPF da Pessoa Física:");
                String cpf = scanner.nextLine();

                try {
                    PessoaFisica pessoaFisica = new PessoaFisica(nomePF, cpf);
                    pessoasFisicasLocacao.add(pessoaFisica);
                    cpfDuplicado = false;
                    System.out.println("\nPessoa cadastrada com sucesso!");
                    System.out.println(
                            "Código do cliente: " + pessoaFisica.getNome() + " = " + pessoaFisica.getProximoCodigo());
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                    System.out.println("Por favor, digite um CPF válido!");
                }
            }
        } else if (opcao == 2) {
            boolean cnpjDuplicado = true;
            while (cnpjDuplicado) {
                System.out.println("\nDigite o nome da Pessoa Jurídica:");
                String nomePJ = scanner.nextLine();
                System.out.println("Digite o CNPJ da Pessoa Jurídica:");
                String cnpj = scanner.nextLine();

                if (verificarCnpjDuplicado(pessoasJuridicasLocacao, cnpj)) {
                    System.out.println("\nCNPJ já cadastrado! Por favor, insira um CNPJ diferente!");
                } else {
                    System.out.println("Digite a razão social:");
                    String nomeEmpresa = scanner.nextLine();
                    PessoaJuridica pessoaJuridica = new PessoaJuridica(nomePJ, cnpj, nomeEmpresa);
                    pessoasJuridicasLocacao.add(pessoaJuridica);
                    System.out.println("\nPessoa cadastrada com sucesso!");
                    System.out.println("Código do cliente: " + pessoaJuridica.getNome() + " = "
                            + pessoaJuridica.getProximoCodigo());
                    cnpjDuplicado = false;
                }
            }
        }
    }

    public static void removerCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {
        System.out.println("Remover Cliente do sistema:\n");
        System.out.print("Digite o código do cliente a ser removido: ");
        int codigoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean clienteRemovido = false;

        for (PessoaFisica pessoa : pessoasFisicasLocacao) {
            if (pessoa.getProximoCodigo() == codigoCliente) {
                pessoasFisicasLocacao.remove(pessoa);
                clienteRemovido = true;
                System.out.println("\nCliente removido com sucesso!!!");
                break;
            }
        }

        if (!clienteRemovido) {
            for (PessoaJuridica pessoa : pessoasJuridicasLocacao) {
                if (pessoa.getProximoCodigo() == codigoCliente) {
                    pessoasJuridicasLocacao.remove(pessoa);
                    clienteRemovido = true;
                    System.out.println("\nCliente removido com sucesso!!!");
                    break;
                }
            }
        }

        if (!clienteRemovido) {
            System.out.println("\nErro ao remover o cliente!!! Verifique o código digitado!");
        }
    }

    public static void pesquisarCliente(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao, Scanner scanner) {

        System.out.print("Digite o código do cliente que deseja encontrar: ");
        int codigoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean clienteEncontrado = false;

        for (PessoaFisica pessoaFisica : pessoasFisicasLocacao) {
            if (pessoaFisica.getProximoCodigo() == codigoCliente) {
                System.out.println("\nCliente físico encontrado: ");
                System.out.println("Nome: " + pessoaFisica.getNome());
                System.out.println("CPF: " + pessoaFisica.getCpf());
                clienteEncontrado = true;
                break;
            }
        }

        if (!clienteEncontrado) {
            for (PessoaJuridica pessoaJuridica : pessoasJuridicasLocacao) {
                if (pessoaJuridica.getProximoCodigo() == codigoCliente) {
                    System.out.println("\nCliente jurídico encontrado: ");
                    System.out.println("Nome: " + pessoaJuridica.getNome());
                    System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
                    System.out.println("Razão Social: " + pessoaJuridica.getRazaoSocial());
                    clienteEncontrado = true;
                    break;
                }
            }
        }

        if (!clienteEncontrado) {
            System.out.println("Cliente não encontrado!!!");
        }
    }

    public static void cadastrarCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.println("\nDigite a marca do carro:");
        String marca = scanner.nextLine();
        System.out.println("Digite o modelo do carro:");
        String modelo = scanner.nextLine();
        System.out.println("Digite o ano do carro:");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        System.out.println("Digite a placa do carro:");
        String placa = scanner.nextLine();
        System.out.println("Digite a quantidade de portas do carro:");
        int quantidadePortas = scanner.nextInt();
        System.out.println("Digite o valor da diária do carro:");
        double valorDiaria = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer
        System.out.println("O carro possui ar condicionado? (S/N):");
        String temArCondicionado = scanner.nextLine();
        boolean arCondicionado = temArCondicionado.equalsIgnoreCase("S");

        Carro carro = new Carro(1, marca, modelo, ano, placa, ano, arCondicionado, valorDiaria, true);
        carrosLocacao.add(carro);
        System.out.println("\nCarro cadastrado com sucesso!");
        System.out.println("Modelo: " + carro.getModelo() + " - Placa: " + carro.getPlaca() + " - Código: "
                + carro.getCarroCodigo());
    }

    public static void removerCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.println("Remover Carro do sistema:\n");
        System.out.print("Digite o código do carro a ser removido: ");
        int codigoCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean carroRemovido = false;

        for (Carro carro : carrosLocacao) {
            if (carro.getCarroCodigo() == codigoCarro) {
                carrosLocacao.remove(carro);
                carroRemovido = true;
                System.out.println("\nCarro removido com sucesso!!!");
                break;
            }
        }

        if (!carroRemovido) {
            System.out.println("\nErro ao remover o carro!!! Verifique o código digitado!");
        }
    }

    public static void pesquisarCarro(ArrayList<Carro> carrosLocacao, Scanner scanner) {
        System.out.print("Digite o código do carro que deseja encontrar: ");
        int codigoCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        boolean carroEncontrado = false;

        for (Carro carro : carrosLocacao) {
            if (carro.getCarroCodigo() == codigoCarro) {
                System.out.println("\nCarro encontrado: ");
                System.out.println("Modelo: " + carro.getModelo());
                System.out.println("Placa: " + carro.getPlaca());
                System.out.println("Ano: " + carro.getAno());
                System.out.println("Quantidade de portas: " + carro.getQuantidadePortas());
                System.out.println("Valor da Diária: " + carro.getValorDiaria());
                System.out.println("Ar Condicionado: " + (carro.isArCondicionado() ? "Sim" : "Não"));
                carroEncontrado = true;
                break;
            }
        }

        if (!carroEncontrado) {
            System.out.println("Carro não encontrado!!!");
        }
    }

    public static void realizarLocacao(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao,
            ArrayList<Carro> carrosLocacao,
            ArrayList<Locacao> locacoes,
            Scanner scanner) {
        System.out.println("\nSelecione o tipo de cliente para a locação:");
        System.out.println("1. Pessoa Física");
        System.out.println("2. Pessoa Jurídica");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        ArrayList<? extends Pessoa> clientesLocacao = (tipoCliente == 1) ? pessoasFisicasLocacao
                : pessoasJuridicasLocacao;

        System.out.println("\nSelecione a Pessoa para a locação:");
        for (int i = 0; i < clientesLocacao.size(); i++) {
            Pessoa cliente = clientesLocacao.get(i);
            System.out.println((i + 1) + ". " + cliente.getNome());
        }

        int indexCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("\nCarros Disponíveis:");
        int index = 0;
        for (Carro carro : carrosLocacao) {
            if (carro.isDisponivel()) {
                System.out.println((index + 1) + ". " + carro.getModelo() + " - Disponível: " +
                        (carro.isDisponivel() ? "Sim" : "Não"));
                index++;
            }
        }

        if (index == 0) {
            System.out.println("\nNenhum carro disponível para locação no momento!");
            return;
        }

        System.out.println("Selecione o carro desejado:");
        int indexCarro = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (indexCarro <= index && indexCarro > 0) {
            Carro carroSelecionado = carrosLocacao.get(indexCarro - 1);
            Pessoa pessoa = clientesLocacao.get(indexCliente - 1);

            LocalDate dataRealizacao = LocalDate.now();

            System.out.println("Informe quantidade de dias que o carro ficará alugado: ");
            int numeroDiarias = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            Locacao novaLocacao = new Locacao(pessoa, carroSelecionado, dataRealizacao, numeroDiarias);
            locacoes.add(novaLocacao);

            carroSelecionado.setDisponivel(false);

            System.out.println("\nLocação realizada com sucesso para: " + pessoa.getNome() +
                    " - Número da Locação: " + novaLocacao.getNumero() +
                    " - Carro: " + carroSelecionado.getModelo() +
                    " - Data de realização: " + dataRealizacao +
                    " - Data máxima devolução: " + novaLocacao.getDataDevolucaoMaxima());
        } else {
            System.out.println("\nErro ao realizar a Locação!");
        }
    }

    public static void pesquisarLocacao(ArrayList<Locacao> locacoes, Scanner scanner) {
        System.out.print("Digite o número da locação que deseja pesquisar: ");
        int numeroLocacao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        Locacao locacaoEncontrada = buscarLocacaoPeloNumero(locacoes, numeroLocacao);

        if (locacaoEncontrada != null) {
            System.out.println("\nLocação encontrada:");
            System.out.println("Número: " + locacaoEncontrada.getNumero());
            System.out.println("Cliente: " + locacaoEncontrada.getCliente().getNome());
            System.out.println("Carro: " + locacaoEncontrada.getCarro().getModelo());
            System.out.println("Data de realização: " + locacaoEncontrada.getDataRealizacao());
            System.out.println("Número de dias alugado: " + locacaoEncontrada.getnDiarias());
            System.out.println("Data de devolução máxima: " + locacaoEncontrada.getDataDevolucaoMaxima());
        } else {
            System.out.println("Locação não encontrada com o número fornecido!");
        }
    }

    private static Locacao buscarLocacaoPeloNumero(ArrayList<Locacao> locacoes, int numeroLocacao) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == numeroLocacao) {
                return locacao;
            }
        }
        return null;
    }

    private static boolean verificarCnpjDuplicado(ArrayList<PessoaJuridica> pessoas, String cnpj) {
        for (PessoaJuridica pessoa : pessoas) {
            if (pessoa.getCnpj().equals(cnpj)) {
                return true; // CNPJ já existe na lista
            }
        }
        return false; // CNPJ não existe na lista
    }

public static void realizarDevolucao(Scanner scanner, ArrayList<LocacaoController.Locacao> locacoes) {
    System.out.print("Digite o número da locação que deseja devolver: ");
    int numeroLocacao = scanner.nextInt();

    // Assuming that you have a method to get LocacaoController instance
    LocacaoController locacaoController = LocacaoController.getInstance();

    LocacaoController.Locacao locacaoParaDevolucao = ((Object) locacaoController).buscarLocacaoPorNumero(numeroLocacao);

    if (locacaoParaDevolucao != null) {
        System.out.print("Digite a data da devolução (AAAA-MM-DD): ");
        String dataDevolucaoStr = scanner.next();
        LocalDate dataDevolucao = LocalDate.parse(dataDevolucaoStr);

        // Assuming that you have a method to get CarrosController instance
        CarrosController carrosController = CarrosController.getInstance();
        
        double valorTotal = locacaoController.calcularValorTotal(locacaoParaDevolucao.getNumeroDiarias(), carrosController.getValorDiaria());

        System.out.println("Valor total da locação: " + valorTotal);

        locacaoController.devolucao(locacaoParaDevolucao, indexCarro);

        System.out.println("Devolução realizada com sucesso para a locação número " + numeroLocacao);
    } else {
        System.out.println("Locação não encontrada com o número fornecido!");
    }
}

    private static Locacao buscarLocacaoPeloCodigo(ArrayList<Locacao> locacoes, int numeroLocacao) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == numeroLocacao) {
                return locacao;
            }
        }
        return null;
    }

    public static void exibirRelatorio(Scanner scanner) {
        System.out.println(" 1 - Clientes  2 - Carros  3 - Locações : \n");
        int opcao = scanner.nextInt();
    
        switch (opcao) {
            case 1:
                exibirRelatorioClientes(scanner);
                break;
            case 2:
                exibirRelatorioCarros(scanner);
                break;
            case 3:
                exibirRelatorioLocacoes(scanner);
                break;
            default:
                System.out.println("Opção inválida!!! Tente novamente!");
        }
    }
    
    private static void exibirRelatorioClientes(Scanner scanner) {
        System.out.println(" 1 - Fisico 2 - Juridico : \n");
        int opcao = scanner.nextInt();
    
        ArrayList<Pessoa> pessoas = PessoaController.getInstance().getPessoa();
    
        for (Pessoa pessoa : pessoas) {
            if ((opcao == 1 && pessoa instanceof PessoaFisica) ||
                (opcao == 2 && pessoa instanceof PessoaJuridica)) {
    
                System.out.println("\nCódigo: " + pessoa.getCodigo());
                System.out.println("Nome: " + pessoa.getNome());
    
                if (pessoa instanceof PessoaFisica) {
                    System.out.println("CPF: " + ((PessoaFisica) pessoa).getCpf() + "\n");
                } else if (pessoa instanceof PessoaJuridica) {
                    PessoaJuridica pessoaJuridica = (PessoaJuridica) pessoa;
                    System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
                    System.out.println("Razão Social: " + pessoaJuridica.getRazaoSocial());
                }
            }
        }
    }
    
    private static void exibirRelatorioCarros(Scanner scanner) {
        System.out.println("\nRelatório de Carros cadastrados: ");
        ArrayList<Carro> carros = CarrosController.getInstance().getCarros();
    
        for (Carro carro : carros) {
            System.out.println("\nCódigo: " + carro.getCarroCodigo());
            System.out.println("Marca: " + carro.getMarca());
            System.out.println("Modelo: " + carro.getModelo());
            System.out.println("Ano: " + carro.getAno());
            System.out.println("Placa: " + carro.getPlaca());
            System.out.println("Quantidade de portas: " + carro.getQuantidadePortas());
            System.out.println("Ar condicionado: " + carro.isArCondicionado());
            System.out.println("Valor da diaria: " + carro.getValorDiaria());
            System.out.println("Estado: " + carro.isDisponivel() + "\n");
        }
    }
    
    private static void exibirRelatorioLocacoes(Scanner scanner) {
        System.out.println("\nRelatório de Locações feitas: ");
        ArrayList<br.edu.ifsp.arq.tsi.inoo.locacao.controller.LocacaoController.Locacao> locacoes = LocacaoController.getInstance().getLocacoes();
    
        for (Locacao locacao : locacoes) {
            System.out.println("\nNúmero: " + locacao.getNumero());
            System.out.println("Cliente: " + locacao.getCliente().getNome());
            for (Carro carro : locacao.getCarros()) {
            System.out.println("Carro: " + locacao.getCarro().getModelo());
            System.out.println("Data da realização: " + locacao.getDataRealizacao());
            System.out.println("Número de dias alugado: " + locacao.getnDiarias());
            System.out.println("Data da devolução: " + locacao.getDtDevolucao());
            }
        }
    }
}