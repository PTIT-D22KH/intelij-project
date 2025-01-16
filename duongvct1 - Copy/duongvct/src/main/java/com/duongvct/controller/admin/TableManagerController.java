package com.duongvct.controller.admin;

import com.duongvct.entity.Table;
import com.duongvct.service.impl.TableServiceImpl;
import com.duongvct.utils.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/table")
public class TableManagerController {

    @Autowired
    private TableServiceImpl tableService;

    @GetMapping("")
    public String showTables(Model model) {
        List<Table> tables = tableService.findAll();
        model.addAttribute("tables", tables);
        return "admin/table/table-management";
    }

    @GetMapping("/add")
    public String addTableForm(Model model) {
        model.addAttribute("table", new Table());
        return "admin/table/add-table";
    }

    @PostMapping("/add")
    public String addTable(@ModelAttribute Table table, Model model) {
        table.setTableStatus(TableStatus.FREE);
        tableService.save(table);
        return "redirect:/admin/table";
    }

    @GetMapping("/edit/{id}")
    public String editTable(@PathVariable Long id, Model model) {
        Table table = tableService.findById(id);
        model.addAttribute("table", table);
        return "admin/table/edit-table";
    }

    @PostMapping("/edit/{id}")
    public String updateTable(@ModelAttribute Table table, @PathVariable Long id) {
        Table existingTable = tableService.findById(id);
        existingTable.setName(table.getName());
        tableService.save(existingTable);
        return "redirect:/admin/table";
    }

    @PostMapping("/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            tableService.deleteById(id);
        }
        return "redirect:/admin/table";
    }

    @GetMapping("/delete/{id}")
    public String deleteTable(@PathVariable Long id) {
        tableService.deleteById(id);
        return "redirect:/admin/table";
    }
}