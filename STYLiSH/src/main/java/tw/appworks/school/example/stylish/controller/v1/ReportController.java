package tw.appworks.school.example.stylish.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.example.stylish.data.dto.ReportPayment;
import tw.appworks.school.example.stylish.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("api/1.0/report")
public class ReportController {

    public static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("payments")
    @ResponseBody
    public ResponseEntity<?> getPayments() {
        List<ReportPayment> ret = reportService.getPayments();
        return ResponseEntity.ok(ret);
    }
    
}
