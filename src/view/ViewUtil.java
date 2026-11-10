package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class ViewUtil {

    static JTextField ajouterChamp(JPanel panel, String label) {
        // Etape simple: ajouter un texte puis un champ dans le formulaire.
        panel.add(new JLabel(label));
        JTextField champ = new JTextField();
        panel.add(champ);
        return champ;
    }

    static DefaultTableModel creerModel(String[] colonnes) {
        // Etape simple: creer un modele de tableau non modifiable directement.
        return new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    static void ajouterAction(JButton bouton, final Runnable action) {
        // Etape simple: relier un bouton a une action.
        bouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    static void ajouterSelectionTable(JTable table, final Runnable action) {
        // Etape simple: executer une action quand une ligne est selectionnee.
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    action.run();
                }
            }
        });
    }

    static int lireInt(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en entier.
        try {
            return Integer.parseInt(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre un entier.");
        }
    }

    static double lireDouble(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en nombre decimal.
        try {
            return Double.parseDouble(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre un nombre.");
        }
    }

    static LocalDate lireDate(JTextField champ, String nomChamp) {
        // Etape simple: convertir un texte en date au format yyyy-mm-dd.
        try {
            return LocalDate.parse(champ.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Le champ " + nomChamp + " doit etre au format yyyy-mm-dd.");
        }
    }

    static String texte(Object valeur) {
        // Etape simple: eviter les erreurs quand une valeur de la base est NULL.
        if (valeur == null) {
            return "";
        }
        return valeur.toString();
    }

    static void afficherResultat(JPanel panel, boolean ok, String message) {
        // Etape simple: afficher un message selon le resultat.
        if (ok) {
            JOptionPane.showMessageDialog(panel, message);
        } else {
            afficherErreur(panel, "Operation refusee. Verifiez les champs.");
        }
    }

    static void afficherErreur(JPanel panel, String message) {
        // Etape simple: afficher une erreur dans une boite de dialogue.
        JOptionPane.showMessageDialog(panel, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
