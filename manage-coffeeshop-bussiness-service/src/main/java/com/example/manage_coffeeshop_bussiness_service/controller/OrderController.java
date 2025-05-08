    package com.example.manage_coffeeshop_bussiness_service.controller;

    import com.example.manage_coffeeshop_bussiness_service.dto.request.OrderRequest;
    import com.example.manage_coffeeshop_bussiness_service.dto.respone.OrderRes;
    import com.example.manage_coffeeshop_bussiness_service.service.OrderService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.parameters.P;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDate;
    import java.util.List;

    @CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
    @RestController
    @Validated
    @RequestMapping("/api/business/order")
    public class OrderController {
        @Autowired private OrderService orderService;

        @PreAuthorize("hasAnyRole('ADMIN','USER')")
        @PostMapping
        public String createOrder(@Valid @RequestBody OrderRequest request){
            System.out.println(request);
            return orderService.createOrder(request);
        }

        @PreAuthorize("hasAnyRole('ADMIN','USER')")
        @GetMapping("/{id}")
        public ResponseEntity<OrderRes> findOrderById(@PathVariable int id){
            return ResponseEntity.ok(orderService.findOrderById(id));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/date")
        public ResponseEntity<List<OrderRes>> findOrderByDate(@RequestParam String date){
            return ResponseEntity.ok(orderService.findOrderByDate(date));
        }

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/date/today")
        public ResponseEntity<List<OrderRes>> findOrderByDate(){
            String currentDate = LocalDate.now().toString();
            return ResponseEntity.ok(orderService.findOrderByDate(currentDate));
        }

    }
