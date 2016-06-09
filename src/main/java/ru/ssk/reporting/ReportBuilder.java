package ru.ssk.reporting;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ssk.exception.DatabaseConnectionException;
import ru.ssk.model.*;
import ru.ssk.service.ReceiptService;

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
    @Autowired
    private ReceiptService receiptService;

    public void generateReceipt(long agreementNumber) {
        Receipt receipt = receiptService.findByAgreementNumber(agreementNumber);

        Enterprise enterprise = receipt.getAgreement().getMeteringPoint().getEnterpriseEntry();
        String temp = "Получатель: " + enterprise.getName() +
                " р/с: " + enterprise.getCheckingAccount() +
                " к/с: " + enterprise.getCorrespondentAccount() + "\r\n" +
                "Банк: " + enterprise.getBank() +
                " БИК " + enterprise.getBankBik() +
                " ИНН " + enterprise.getBankInn() +
                " КПП " + enterprise.getBankKpp() + "\r\n" +
                enterprise.getBankAddress().getIndex() +
                ", г. " + enterprise.getBankAddress().getCity() +
                ", ул. " + enterprise.getBankAddress().getStreet() +
                ", " + enterprise.getBankAddress().getBuilding();
        TextFieldBuilder<String> text1 = cmp.text(temp);

        Owner owner = receipt.getAgreement().getMeteringPoint().getOwner();
        StringBuilder sender = new StringBuilder("Плательщик: ");
        if (owner instanceof LegalEntity) {
            sender.append(((LegalEntity) owner).getName()).append("; Счёт ").append(owner.getPersonalAccount());
        } else {
            PhysicalPerson person = (PhysicalPerson) owner;
            sender.append(person.getLastName()).append(" ").append(person.getFirstName()).append(" ");
            if (person.getMiddleName() != null) {
                sender.append(person.getMiddleName());
            }
            sender.append("; Счёт ").append(owner.getPersonalAccount());
        }
        TextFieldBuilder<String> text2 = cmp.text(sender.toString());

        Address address = receipt.getAgreement().getMeteringPoint().getAddress();
        temp = "Адрес объекта: " + address.getCity() + ", " + address.getStreet() + ", "
                + address.getBuilding();
        if (address.getApartment() != null) {
            temp += ", " + address.getApartment();
        }
        TextFieldBuilder<String> text3 = cmp.text(temp);

        StyleBuilder style = stl.style();
        style.setBorder(stl.pen1Point())
                //.setLeftBorder(stl.pen1Point()).setRightBorder(stl.pen1Point()).setTopBorder(stl.pen1Point()).setBottomBorder(stl.pen1Point())
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

        JasperReportBuilder report = DynamicReports.report();
        try (Connection connection = dataSource.getConnection()) {
            report.setColumnStyle(style)
                    .columns(//col.reportRowNumberColumn("№").setFixedColumns(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                            col.column("Содержание заказа", "service", DataTypes.stringType()),
                            col.column("Кол-во", "count", DataTypes.integerType()),
                            col.column("Коэф.", "coefficient", DataTypes.stringType()),
                            col.column("Цена за ед.", "price_per", DataTypes.bigDecimalType()),
                            col.column("Стоимость", "price_sum", DataTypes.bigDecimalType()),
                            col.column("НДС", "price_vad", DataTypes.bigDecimalType()),
                            col.column("Итого", "total", DataTypes.bigDecimalType()))//.setColumnStyle(stl.style().setBorder())
                    .title(cmp.text("Квитанция № " + receipt.getNumber()), text1, text2, text3)//shows report title
                    .addDetailFooter(cmp.text("Назначение платежа: " + receipt.getPaymentPurpose()))
                    .setDataSource("SELECT \"es\".name as \"service\", \n" +
                                    "\"sia\".count as \"count\", \n" +
                                    "\"sia\".coefficient as \"coefficient\", \n" +
                                    "\"es\".price as \"price_per\", \n" +
                                    "\"es\".price * 1.18 as \"price_sum\", \n" +
                                    "\"es\".price * 0.18 as \"price_vad\", \n" +
                                    "\"a\".total as \"total\" \n" +
                                    "FROM extra_service \"es\" \n" +
                                    "JOIN service_in_agreement \"sia\" ON \"sia\".extra_service = \"es\".id \n" +
                                    "JOIN agreement \"a\" ON \"a\".agreement_num = \"sia\".agreement_num \n" +
                                    "WHERE \"a\".agreement_num = " + receipt.getAgreement().getNumber() + ";",
                        connection);
            report.show(false);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Не удалось подключиться к базе данных.");
        } catch (DRException e) {
            throw new DatabaseConnectionException("Не удалось сгенерировать отчёт.");
        }
    }

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
