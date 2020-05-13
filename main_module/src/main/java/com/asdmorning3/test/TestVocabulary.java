package com.asdmorning3.test;

import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.basic.Tags;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TestVocabulary {
    static JFrame frame;
    private static JDialog d;
    static JList list_vocabs;
    static JList list_test;
    public JLabel lblVocableList = new JLabel();
    public JLabel lblTestList = new JLabel();
    public JLabel lblRepetition = new JLabel();
    public JLabel lblLanguages = new JLabel();
    public JButton button_start = new JButton();
    public JButton button_show = new JButton();
    public JButton button_add_all = new JButton();
    public JButton button_remove_all = new JButton();
    public JComboBox<Vocable.Language> comboBoxFromLang;
    public JComboBox<Vocable.Language> comboBoxToLang;
    public JComboBox<Tags> comboBoxTags;
    public JComboBox<String> comboBoxRating;
    public JTextField txtNumber = new JTextField();
    VocableDictionary dictionary;
    InterfaceLanguages.Languages interface_languages;
    InterfaceLanguages languages;
    //JTextField t1;
    HashMap<String, Vocable.Language> language_list = new HashMap();
    ArrayList<Vocable> vocab_array = new ArrayList<>();
    ArrayList<Vocable> test_array = new ArrayList<>();

    private java.util.List<Vocable> vocableList;

    public TestVocabulary(VocableDictionary dictionary_, InterfaceLanguages.Languages lang) {
        languages = new InterfaceLanguages();
        dictionary = dictionary_;
        interface_languages = lang;
        frame = new JFrame("Test Interface");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        ArrayList<String> languages = new ArrayList<String>();
        for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
            languages.add(language.name());
            language_list.put(language.name(), language);
        }

        comboBoxFromLang = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        comboBoxToLang = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        comboBoxTags = new JComboBox(dictionary.getTagsString().toArray(new String[0]));
        comboBoxRating = new JComboBox();
        final JComboBox setAnotherLanguage;

        frame.setSize(600, 600);
        c.insets = new Insets(10, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(comboBoxTags, c);

        c.gridx = 1;
        c.gridy = 0;
        pane.add(comboBoxRating, c);

        //c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.gridx = 2;
        c.gridy = 0;
        pane.add(button_show, c);

        c.gridx = 0;
        c.gridy = 1;
        pane.add(lblLanguages, c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(comboBoxFromLang, c);

        c.gridx = 2;
        c.gridy = 1;
        pane.add(comboBoxToLang, c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(lblVocableList, c);

        c.gridx = 2;
        c.gridy = 2;
        pane.add(lblTestList, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 5;
        list_vocabs = new JList();
        JScrollPane scroll_vocabs = new JScrollPane(list_vocabs);
        pane.add(scroll_vocabs, c);

        c.gridx = 2;
        c.gridy = 3;
        c.gridheight = 5;
        list_test = new JList();
        JScrollPane scroll_test = new JScrollPane(list_test);
        pane.add(scroll_test, c);

        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 9;
        pane.add(button_add_all, c);

        c.gridx = 2;
        c.gridy = 9;
        pane.add(button_remove_all, c);

        c.gridx = 0;
        c.gridy = 10;
        pane.add(lblRepetition, c);

        c.gridx = 0;
        c.gridy = 11;
        pane.add(txtNumber, c);

        c.gridx = 2;
        c.gridy = 11;
        pane.add(button_start, c);


        frame.add(pane);
        button_show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = comboBoxFromLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);

                Object obj2 = comboBoxToLang.getSelectedItem();
                String secondCurrentLanguage = String.valueOf(obj2);

                if (currentLanguage != secondCurrentLanguage) {
                    vocab_array = new ArrayList<Vocable>(dictionary.getAllFromLanguage(language_list.get(currentLanguage)));
                    int size = vocab_array.size();
                    String[] words = new String[size];
                    for (int counter = 0; counter < vocab_array.size(); counter++) {
                        words[counter] = vocab_array.get(counter).getWord();
                    }
                    final String[] final_list = words;
                    list_vocabs.setListData(final_list);
                } else
                    JOptionPane.showMessageDialog(frame,
                            "Select two different languages!",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);
            }

        });


        list_vocabs.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = comboBoxFromLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()) {
                    JList source = (JList) event.getSource();
                    String selected = source.getSelectedValue().toString();
                    HashSet<Vocable> test = dictionary.getVocableList();
                    for (Vocable i : test)
                        if (i.getWord().equals(selected)) {
                            test_array.add(i);
                        }
                }
            }

        });

        list_test.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = comboBoxToLang.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()) {
                    JList source = (JList) event.getSource();
                    String selected = source.getSelectedValue().toString();
                    HashSet<Vocable> test = dictionary.getVocableList();
                    for (Vocable i : test)
                        if (i.getWord().equals(selected)) {
                        }
                }
            }

        });
        setIntLang(lang);

    }

    public void setIntLang(InterfaceLanguages.Languages lang) {
        lblVocableList.setText(languages.getString(lang, "vocablist"));
        lblTestList.setText(languages.getString(lang, "testlist"));
        lblRepetition.setText(languages.getString(lang, "repetition"));
        lblLanguages.setText(languages.getString(lang, "languages"));
        frame.setTitle(languages.getString(lang, "test"));
        button_start.setText(languages.getString(lang, "start"));
        button_show.setText(languages.getString(lang, "show"));
        button_add_all.setText(languages.getString(lang, "addall"));
        button_remove_all.setText(languages.getString(lang, "removeall"));
    }

    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    public Component getContent()
    {
        return frame.getContentPane();
    }
}

