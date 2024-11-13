package GRAPHICAL_USER_INTERFACE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrutaManagerGUI {
	
    // declaração de variáveis que armazenam os dados e a interface da lista
    private ArrayList<String> frutas;
    private DefaultListModel<String> listModel;
    private JList<String> list;

    // construtor da classe que configura a interface grafica da aplicação
    public FrutaManagerGUI() {
    	
        // inicializa a lista de frutas e o modelo de dados da JList
        frutas = new ArrayList<>();
        listModel = new DefaultListModel<>();

        // configura a janela principal da aplicação
        JFrame frame = new JFrame("Gerenciador de Frutas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());

        // criação de um painel superior com um campo de texto e botões
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // campo de texto para entrada de frutas
        JTextField frutaField = new JTextField(15);
        panel.add(frutaField);
        
        // criação de botões com ações para adicionar, modificar e remover frutas
        JButton addButton = new JButton("Adicionar"); 
        panel.add(addButton);

        // botão que vai modificar uma fruta que ja existe
        JButton modifyButton = new JButton("Modificar");
        
        // inicialmente esta desativado
        modifyButton.setEnabled(false);
        panel.add(modifyButton);
        
        // botão que serve para remover alguma fruta
        JButton removeButton = new JButton("Remover");
        
        // inicialmente desativado
        removeButton.setEnabled(false);
        panel.add(removeButton);

        // adiciona o painel superior à parte superior da janela
        frame.add(panel, BorderLayout.NORTH);

        // configuração da JList e do painel de rolagem que a contém
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);

        // botão na parte inferior da janela para listar todas as frutas
        JButton listButton = new JButton("Listar Frutas");
        frame.add(listButton, BorderLayout.SOUTH);

        // ação do botão "Adicionar" que insere uma nova fruta na lista
        addButton.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		// obtem o texto digitado pela pessoa
                String novaFruta = frutaField.getText();
                
                // verifica se o campo não esta vazio
                if (!novaFruta.isEmpty()) { 
                	// adiciona ao ArrayList
                    frutas.add(novaFruta); 
                 	// adiciona ao modelo da Jlist
                    listModel.addElement(novaFruta); 
                 	// limpa o campo de texto
                    frutaField.setText(""); 
                 	// exibe a informação
                    JOptionPane.showMessageDialog(frame, novaFruta + " foi adicionada."); 
                }
            }
        });

        // ação do botão "Modificar" que permite alterar o nome de uma fruta selecionada
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 // obtem o índice do item selecionado
                int selectedIndex = list.getSelectedIndex();
                // verifica se um item foi selecionado
                if (selectedIndex != -1) { 
                    String frutaSelecionada = listModel.getElementAt(selectedIndex);
                    // vai abri um dialogo para que a pessoa possa escrever o novo nome de alguma fruta
                    String novaFruta = JOptionPane.showInputDialog(frame, "Modificar " + frutaSelecionada + " para:", frutaSelecionada);
                    // verifica se a entrada não é nula ou vazia
                    if (novaFruta != null && !novaFruta.isEmpty()) {
                    	// atualiza o ArrayList
                        frutas.set(selectedIndex, novaFruta); 
                        // atualiza na Jlist
                        listModel.set(selectedIndex, novaFruta); 
                        JOptionPane.showMessageDialog(frame, "Fruta " + frutaSelecionada + " foi modificada para " + novaFruta + ".");
                    }
                } else {
                	// aparece uma mensagem caso nenhuma fruta ja foi selecionada
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para modificar."); 
                }
            }
        });

        // vai remover a fruta que a pessoa selecionou quando apertar em "Remover" 
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// obetem o indice do item que foi selecionado pela pessoa
                int selectedIndex = list.getSelectedIndex(); 
                if (selectedIndex != -1) {
                	 // Remove do ArrayList
                    String frutaRemovida = frutas.remove(selectedIndex);
                    // remove daJlist
                    listModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(frame, frutaRemovida + " foi removida.");
                } else {
                	// Mensagem caso nenhuma fruta esteja selecionada
                    JOptionPane.showMessageDialog(frame, "Selecione uma fruta para remover."); 
                }
            }
        });

        // ação do botão "Listar Frutas" que exibe todas as frutas armazenadas
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frutas.isEmpty()) {
                	// mensagem se a lista estiver vazia
                    JOptionPane.showMessageDialog(frame, "Nenhuma fruta na lista."); 
                } else {
                	// aparece a lista de frutas no ArrayList
                    JOptionPane.showMessageDialog(frame, "Frutas: " + frutas); 
                }
            }
        });

        // ouvinte que habilita/desabilita os botões de "Modificar" e "Remover" com base na seleção
        list.addListSelectionListener(e -> {
            boolean selectionExists = !list.isSelectionEmpty();
            removeButton.setEnabled(selectionExists);
            modifyButton.setEnabled(selectionExists);
        });

        // Tornando a janela visível
        frame.setVisible(true);
    }

    // metodo principal para iniciar a aplicação
    public static void main(String[] args) {
        // Garante que a GUI seja criada na thread correta
        SwingUtilities.invokeLater(() -> new FrutaManagerGUI());
    }
}