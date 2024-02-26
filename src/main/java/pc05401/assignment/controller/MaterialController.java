package pc05401.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Ingredient;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.IngredientRepository;
import pc05401.assignment.service.SessionService;

@Controller
public class MaterialController {

	@Autowired
	SessionService session;

	@Autowired
	private IngredientRepository ingredientRepository;

	@GetMapping("checkMaterials")
	public String checkMaterials(Model model) {
		List<Ingredient> listItem = ingredientRepository.findAll();
		System.out.println(listItem);
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("checkMaterials/add/{ingredientId}")
	public String addOneMaterials(@PathVariable int ingredientId, Model model) {
		ingredientRepository.addOneToAmount(ingredientId);

		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("checkMaterials/minus/{ingredientId}")
	public String minusOneMaterials(@PathVariable int ingredientId, Model model) {
		ingredientRepository.subtractOneFromAmount(ingredientId);

		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);

		return checkAdmin("checkMaterials");
	}

	@GetMapping("materialManager")
	public String materialManager(Model model) {
		List<Ingredient> listItem = ingredientRepository.findAll();
		model.addAttribute("listItem", listItem);
		return checkAdmin("materialManager");
	}

	@GetMapping("materialAdd")
	public String showMaterialAdd() {

		return checkAdmin("materialAdd");
	}

	@GetMapping("deleteMaterial/{ingredientId}")
	public String deleteMaterial(@PathVariable int ingredientId) {
		ingredientRepository.deleteById(ingredientId);
		return checkAdmin("redirect:/materialManager");
	}

	@PostMapping("materialAdd")
	public String addMaterial(@ModelAttribute Ingredient ingredient, Model model) {
		addIngredient(ingredient);
		
		return checkAdmin("materialAdd");
	}

	@GetMapping("materialUpdate")
	public String showMaterialUpdate(@RequestParam("ingredientId") int ingredientId, Model model) {
		model.addAttribute("ingredientEdit", ingredientRepository.findById(ingredientId).orElseThrow()) ;
		return checkAdmin("materialUpdate");
	}
	
	@PostMapping("materialUpdate")
	public String updtMaterial(@RequestParam int ingredientId, @RequestParam String name, @RequestParam String unit, @RequestParam int amount, @RequestParam int minAmount, @RequestParam String note) {
		Ingredient ingredientEdit = ingredientRepository.findById(ingredientId).orElseThrow() ;
		
		ingredientEdit.setName(name);
		ingredientEdit.setUnit(unit);
		ingredientEdit.setAmount(amount);
		ingredientEdit.setMinAmount(minAmount);
		ingredientEdit.setNote(note);
		
		ingredientRepository.save(ingredientEdit) ;
		return checkAdmin("materialUpdate");
	}

	public String checkAdmin(String path) {
		StaffModel account = session.get("staff");
		if (account == null) {
			return "redirect:/login";
		}
		if (account.getRole().equals("USER")) {
			return "redirect:/home";
		}
		return path;
	}

	@Transactional
	public void addIngredient(Ingredient ingredient) {
		ingredientRepository.save(ingredient);
	}
}
