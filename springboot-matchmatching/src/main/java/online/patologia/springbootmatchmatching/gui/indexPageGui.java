package online.patologia.springbootmatchmatching.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import online.patologia.springbootmatchmatching.model.MatchModel;
import online.patologia.springbootmatchmatching.model.MatchPlayer;
import online.patologia.springbootmatchmatching.repo.MatchModelRepo;
import online.patologia.springbootmatchmatching.repo.MatchPlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.Notification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("")
public class indexPageGui extends VerticalLayout {

    @Autowired
    private MatchPlayerRepo matchPlayerRepo;
    @Autowired
    private MatchModelRepo matchModelRepo;
    ListBox<String> listaGraczy = new ListBox<>();
    @Autowired
    public indexPageGui() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        //adding new match gui
        TextField place = new TextField("Wprowadź miejsce rozgrywki");
        Label pickDateLabel = new Label("Wybierz datę");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        NumberField numberOfPlayingPeople = new NumberField("Wprowadź liczbę graczy");
        Button addButton = new Button("Dodaj mecz");

//        addButton.addClickListener(event -> {
//            matchModelRepo.save()
//        });
        Grid<MatchModel> gridMatchModel = new Grid<>();


        addAttachListener(event -> {
          //  gridMatchModel.addColumn(MatchModel::getPlace).setHeader("Place");\
           //    gridMatchModel.addColumn(MatchModel::ActualNumberOfPlayers).setHeader("Number of actual players");
           //gridMatchModel.removeColumnByKey("players");

            gridMatchModel.setItems(matchModelRepo.findAll());
            gridMatchModel.addColumn(MatchModel::getDate).setHeader("Data");
            gridMatchModel.addColumn(MatchModel::getPlace).setHeader("Miejsce");


            gridMatchModel.setItemDetailsRenderer(new ComponentRenderer<>(matchModel -> {

                List<String> lista = new ArrayList<>();
                VerticalLayout layout = new VerticalLayout();
//List<MatchPlayer> listaGraczy2 = new ArrayList<>();
                matchPlayerRepo.findAll().stream()
                        .filter(matchPlayer -> matchPlayer.getMatchId()==matchModel.getId())
                        .forEach(matchPlayer ->lista.add(matchPlayer.getUsername()));
                listaGraczy.setItems(lista);

                layout.add(listaGraczy);
                return layout;
            }));

            //adding button
            addButton.addClickListener(buttonClickEvent -> {
                MatchModel actualGame = matchModelRepo.save(new MatchModel(place.getValue(),datePicker.getValue(),numberOfPlayingPeople.getValue().intValue()));
                matchPlayerRepo.save(new MatchPlayer(actualGame.getId(),username));
                UI.getCurrent().getPage().reload();
            });


            //column with the free slots
            gridMatchModel.addColumn(new ComponentRenderer<>(matchModel -> {
                Label labelNumberofPlayers = new Label();
                int liczba = matchPlayerRepo.findByMatchId(matchModel.getId()).size();
                int freeSlots = matchModel.getNumberOfPlayers();
                labelNumberofPlayers.setText(Integer.toString(freeSlots-liczba));
                // layouts for placing the text field on top of the buttons
                HorizontalLayout labelLayout = new HorizontalLayout(labelNumberofPlayers);
                return new VerticalLayout( labelLayout);
            })).setHeader("Wolne miejsca");


            gridMatchModel.addColumn(new ComponentRenderer<>(matchModel -> {

                Button add = new Button("Dołącz", buttonClickEvent -> {
                    if (matchPlayerRepo.policz(matchModel.getId()) < matchModel.getNumberOfPlayers()) {
                        gridMatchModel.deselectAll();
                        if (matchPlayerRepo.findByWszystko(matchModel.getId(), username) == null) {
                            matchPlayerRepo.save(new MatchPlayer(matchModel.getId(), username));
                        }
                        gridMatchModel.select(matchModel);
                    }
                });
                // button for saving the name to backend
                Button update = new Button("Zaktualizuj", buttonClickEvent -> {

                });

                // button that removes the item
                Button remove = new Button("Usuń się", buttonClickEvent -> {
                    gridMatchModel.deselectAll();
                        matchPlayerRepo.deleteMojWlasny(matchModel.getId(),username);
                        gridMatchModel.select(matchModel);
                });

                // layouts for placing the text field on top of the buttons
                HorizontalLayout buttons = new HorizontalLayout(add,update,remove);
                return new VerticalLayout( buttons);
            })).setHeader("Actions");



            add(place,pickDateLabel,datePicker,numberOfPlayingPeople,addButton,gridMatchModel);
        });

    }

}
