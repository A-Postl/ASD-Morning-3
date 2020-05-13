package com.asdmorning3.test;

import com.asdmorning3.basic.Tags;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;


public class studyInterface {
    static JFrame frame;
    private static JDialog d;
    static JList list;
    public JLabel lblLang1 = new JLabel();
    public JLabel lblLang2 = new JLabel();
    public JButton button = new JButton();
    public JButton btnsort;
    public JButton btnsortalphaasc, btnsortalphadesc;
    public JButton btnsortratingasc, btnsortratingdesc;
    public JButton btnratingsubmit, btntagsubmit;
    public JComboBox<Vocable.Rating> comboboxrating;
    public JComboBox<String> comboboxtag;
    public JLabel lblsort, lblfilter;
    VocableDictionary dictionary;
    InterfaceLanguages.Languages interface_languages;
    InterfaceLanguages languages;
    JTextField t1;
    HashMap<String, Vocable.Language> language_list = new HashMap();

    private java.util.List<Vocable> vocableList;

    public studyInterface(VocableDictionary dictionary_, InterfaceLanguages.Languages lang) {
        languages = new InterfaceLanguages();
        dictionary = dictionary_;
        interface_languages = lang;
        frame = new JFrame("Study Interface");
        JPanel pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.setSize(600, 600);

        ArrayList<String> languages = new ArrayList<String>();
        for (Vocable.Language language : Vocable.Language.class.getEnumConstants()) {
            languages.add(language.name());
            language_list.put(language.name(), language);
        }

        final JComboBox fromLanguage = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        final JComboBox toLanguage = new JComboBox(new DefaultComboBoxModel<String>(languages.toArray(new String[0])));
        final JComboBox setAnotherLanguage;

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblLang1, c);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblLang2, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 55, 10, 100);
        pane.add(fromLanguage, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 10, 55);
        pane.add(toLanguage, c);

        c.gridx = 1;
        c.gridy = 4;
        pane.add(button, c);

        c.gridx = 1;
        c.gridy = 5;
        list = new JList();
        JScrollPane scrollPane = new JScrollPane(list);

        pane.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 5;
        t1 = new JTextField();
        pane.add(t1, c);

        c.gridx = 1;
        c.gridy = 6;
        btnsort = new JButton("sort/filter");
        btnsort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame("sort/filter");
                frame2.setSize(600, 400);
                JPanel pane1 = new JPanel(new GridBagLayout());
                GridBagConstraints c1 = new GridBagConstraints();
                lblsort = new JLabel("sort vocabulary");
                c1.gridx = 1;
                c1.gridy = 0;
                pane1.add(lblsort, c1);
                btnsortalphaasc = new JButton("sort alphabetically ascending");
                c1.gridx = 0;
                c1.gridy = 1;
                c1.insets = new Insets(10, 10, 10, 10);
                btnsortalphaasc.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        list.setModel(sortJlist(list));

                    }

                });
                pane1.add(btnsortalphaasc, c1);
                btnsortalphadesc = new JButton("sort alphabetically descending");
                c1.gridx = 2;
                c1.gridy = 1;
                pane1.add(btnsortalphadesc, c1);
                btnsortratingasc = new JButton("sort by rating ascending");
                c1.gridx = 0;
                c1.gridy = 2;
                pane1.add(btnsortratingasc, c1);
                btnsortratingdesc = new JButton("sort by rating descending");
                c1.gridx = 2;
                c1.gridy = 2;
                pane1.add(btnsortratingdesc, c1);
                lblfilter = new JLabel("filter vocabulary");
                c1.gridx = 1;
                c1.gridy = 3;
                pane1.add(lblfilter, c1);
                comboboxrating = new JComboBox<>(Vocable.Rating.values());
                c1.gridx = 0;
                c1.gridy = 4;
                pane1.add(comboboxrating, c1);
                btnratingsubmit = new JButton("filter by selected rating");
                c1.gridx = 2;
                c1.gridy = 4;
                pane1.add(btnratingsubmit, c1);
                ArrayList<Tags> taglist = dictionary_.getTagsList();
                ArrayList<String> taglist2 = new ArrayList<String>();
                for (Tags tag : taglist)
                {
                    taglist2.add(tag.getDescription());
                }
                comboboxtag = new JComboBox<>();
                comboboxtag.setModel(new DefaultComboBoxModel<String>(taglist2.toArray(new String[0])));
                c1.gridx = 0;
                c1.gridy = 5;
                pane1.add(comboboxtag, c1);

                btntagsubmit = new JButton("filter by selected tag");
                c1.gridx = 2;
                c1.gridy = 5;
                pane1.add(btntagsubmit, c1);

                frame2.add(pane1);
                frame2.setVisible(true);
            }

        });
        pane.add(btnsort, c);


        frame.add(pane);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object obj = toLanguage.getSelectedItem();
                String currentLanguage = String.valueOf(obj);

                Object obj2 = fromLanguage.getSelectedItem();
                String secondCurrentLanguage = String.valueOf(obj2);

                if (currentLanguage != secondCurrentLanguage) {
                    vocableList = dictionary.getAllFromLanguage(language_list.get(currentLanguage));
                    int size = vocableList.size();
                    String[] words = new String[size];
                    for (int counter = 0; counter < vocableList.size(); counter++) {
                        words[counter] = vocableList.get(counter).getWord();
                    }
                    final String[] final_list = words;
                    list.setListData(final_list);
                } else
                    JOptionPane.showMessageDialog(frame,
                            "Select two different languages!",
                            "WARNING",
                            JOptionPane.WARNING_MESSAGE);

            }

        });


        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                Object obj = fromLanguage.getSelectedItem();
                String currentLanguage = String.valueOf(obj);
                if (!event.getValueIsAdjusting()) {
                    JList source = (JList) event.getSource();
                    if (!source.isSelectionEmpty()) {
                        String selected = source.getSelectedValue().toString();
                        HashSet<Vocable> test = dictionary.getVocableList();
                        for (Vocable i : test)
                            if (i.getWord().equals(selected)) {
                                t1.setText(i.getTranslation(language_list.get(currentLanguage)).getWord());
                            }
                    }
                }
            }

        });
        setIntLang(lang);

    }

    public void setIntLang(InterfaceLanguages.Languages lang) {
        lblLang1.setText(languages.getString(lang, "from"));
        lblLang2.setText(languages.getString(lang, "to"));
        button.setText(languages.getString(lang, "show"));
        frame.setTitle(languages.getString(lang, "study"));
    }

    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    public Component getContent()
    {
        return frame.getContentPane();
    }

    public DefaultListModel sortJlist(JList list)
    {
        DefaultListModel<String> model
                = new DefaultListModel<>();
        ArrayList<String> sortedVocables = new ArrayList<>();
        for(int i = 0 ; i < list.getModel().getSize();i++)
        {
            sortedVocables.add((String) (list.getModel().getElementAt(i)));
        }

        Collections.sort(sortedVocables);

        for(String val : sortedVocables)
            model.addElement(val);

        return model;
    }
}
