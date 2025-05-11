package generalManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.*;

public class AutoSuggestUtils implements KeyListener {
    public static void attachAutoSuggest(JTextField textField, Map<String, Player> dataMap) {
        JPopupMenu suggestionsPopupMenu = new JPopupMenu();
        suggestionsPopupMenu.setFocusable(false);

        textField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            private void updateSuggestions() {
                String input = textField.getText().toLowerCase();
                suggestionsPopupMenu.setVisible(false);
                suggestionsPopupMenu.removeAll();

                if(input.isEmpty()) {
                    return;
                }

                List<String> suggestions = dataMap.keySet().stream()
                    .filter(key -> key.toLowerCase().startsWith(input.toLowerCase()))
                    .sorted()
                    .collect(Collectors.toList());

                if(suggestions.isEmpty()) {
                    return;
                }

                for(String suggestion : suggestions) {
                    JMenuItem menuItem = new JMenuItem(suggestion);
                    menuItem.addActionListener(e -> {
                        textField.setText(suggestion);
                        suggestionsPopupMenu.setVisible(false);
                    });
                    suggestionsPopupMenu.add(menuItem);
                }

                suggestionsPopupMenu.show(textField, 0, textField.getHeight());
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
