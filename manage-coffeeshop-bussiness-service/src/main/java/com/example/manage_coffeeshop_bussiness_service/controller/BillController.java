    package com.example.manage_coffeeshop_bussiness_service.controller;

    import com.example.manage_coffeeshop_bussiness_service.dto.request.BillReq;
    import com.example.manage_coffeeshop_bussiness_service.dto.respone.BillRes;
    import com.example.manage_coffeeshop_bussiness_service.service.BillService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @RestController
    @Validated
    @RequestMapping("/api/business/bills")
    public class BillController {
        @Autowired
        private BillService billService;


        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public List<BillRes> getAllBills() {
            return billService.getAllBills();
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public BillRes getBillById(@PathVariable int id) {
            return billService.getBillById(id);
        }

        @PostMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public BillRes createBill(@Valid @RequestBody BillReq req) {
            return billService.createBill(req);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
        public BillRes updateBill(@PathVariable int id, @Valid @RequestBody BillReq req) {
            return billService.updateBill(id, req);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public String deleteBill(@PathVariable int id) {
            return billService.deleteBill(id);
        }
    }