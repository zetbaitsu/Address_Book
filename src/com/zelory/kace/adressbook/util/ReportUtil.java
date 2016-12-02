package com.zelory.kace.adressbook.util;

import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.data.model.Person;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.MultiPageListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;


public class ReportUtil {
    private static ReportUtil INSTANCE;

    private final StyleBuilder titleStyle;
    private final StyleBuilder textStyle;

    public static ReportUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ReportUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReportUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ReportUtil() {
        titleStyle = stl.style().setPadding(4).bold().setFontSize(14);
        textStyle = stl.style().setFontSize(12);
    }

    public JasperReportBuilder createReport(AddressBook addressBook) {
        JasperReportBuilder report = report();
        report.setPageFormat(PageType.A4)
                .setTextStyle(textStyle)
                .title(cmp.text(addressBook.getName()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                        cmp.verticalGap(20), createComponents(addressBook));

        return report;
    }

    private MultiPageListBuilder createComponents(AddressBook addressBook) {
        MultiPageListBuilder list = cmp.multiPageList();
        for (int i = 0; i < addressBook.getPersons().size(); i += 2) {
            HorizontalListBuilder row = cmp.horizontalList();
            row.add(createPersonComponent(addressBook.getPersons().get(i)));
            row.add(cmp.horizontalGap(12));
            row.add(i + 1 < addressBook.getPersons().size() ? createPersonComponent(addressBook.getPersons().get(i + 1)) : createEmptyComponent());
            list.add(row).add(cmp.verticalGap(12));
        }
        return list;
    }

    private ComponentBuilder<?, ?> createEmptyComponent() {
        return cmp.verticalList(cmp.horizontalList(cmp.horizontalGap(20), cmp.verticalList(cmp.text("")), cmp.horizontalGap(20)));
    }

    private ComponentBuilder<?, ?> createPersonComponent(Person person) {
        VerticalListBuilder content = cmp.verticalList(
                cmp.text(person.getName()),
                cmp.text(person.getAddress()),
                cmp.text(String.format("%s %s", person.getCity(), person.getZip())),
                cmp.text(person.getState()));
        return createCellComponent("To", content);
    }

    private ComponentBuilder<?, ?> createCellComponent(String label, ComponentBuilder<?, ?> content) {
        VerticalListBuilder cell = cmp.verticalList(cmp.text(label).setStyle(titleStyle),
                cmp.horizontalList(cmp.horizontalGap(20), content, cmp.horizontalGap(20)));
        cell.setStyle(stl.style(stl.pen1Point()));
        return cell;
    }
}
