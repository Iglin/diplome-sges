package ru.ssk.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.exception.DatabaseConnectionException;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

/**
 * Created by user on 01.06.2016.
 */
public class ReportBuilder {
    @Autowired
    private DataSource dataSource;

    public void generateAgreementsRegistry(Date dateFrom, Date dateTo, boolean byEntities) throws DRException {
        JasperReportBuilder report = DynamicReports.report();
        StyleBuilder boldStyle         = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);

        try (Connection connection = dataSource.getConnection()) {
            report
                    .setColumnTitleStyle(columnTitleStyle)
                    .highlightDetailEvenRows()
                    .columns(col.reportRowNumberColumn("№").setFixedColumns(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                            col.column("Номер договора", "agreement_num", DataTypes.longType()),
                            col.column("Дата", "date", DataTypes.dateType()),
                            col.column("Заказчик", "name", DataTypes.stringType()),
                            col.column("Адрес", "address", DataTypes.stringType()),
                            col.column("Сумма", "total", DataTypes.bigDecimalType()))//.setColumnStyle(stl.style().setBorder())
                    .title(cmp.text("Реестр договоров с " + dateFrom.toString() + " по " + dateTo.toString()).setStyle(boldCenteredStyle))//shows report title
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle));
            if (byEntities) {
                report.setDataSource("SELECT a.agreement_num, a.\"date\", o.name, " +
                                "p.city || ', ' ||  p.street || ', ' ||  p.building || ', ' ||  p.apartment  \"address\", " +
                                "a.total \n" +
                                "FROM agreement a JOIN metering_point mp ON a.point = mp.id " +
                                "JOIN address p ON mp.address = p.id " +
                                "JOIN legal_entity o ON o.id = mp.owner " +
                                "WHERE a.\"date\" between '" + dateFrom.toString() + "' AND '" + dateTo.toString() + "';",
                        connection);
            } else {
                report.setDataSource("SELECT a.agreement_num, a.\"date\", \n" +
                                "o.lastname || ' ' || o.firstname || ' ' || o.middlename \"name\", \n" +
                                "p.city || ', ' ||  p.street || ', ' ||  p.building || ', ' ||  p.apartment  \"address\", \n" +
                                "a.total \n" +
                                "FROM agreement a JOIN metering_point mp ON a.point = mp.id \n" +
                                "JOIN address p ON mp.address = p.id\n" +
                                "JOIN person o ON o.id = mp.owner \n" +
                                "WHERE a.\"date\" between '" + dateFrom.toString() + "' AND '" + dateTo.toString() + "';",
                        connection);
            }
            report.show(false);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Не удалось подключиться к базе данных.");
        }
    }
}
