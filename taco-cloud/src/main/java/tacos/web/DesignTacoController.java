package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;




@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
					new Ingredient("FLTO","Flour Tortilla", tacos.Ingredient.Type.WRAP),
					new Ingredient("COTO","Corn Tortilla", tacos.Ingredient.Type.WRAP),
					new Ingredient("GRBF","Ground Beef", tacos.Ingredient.Type.PROTEIN),
					new Ingredient("CARN","Carnitas", tacos.Ingredient.Type.PROTEIN),
					new Ingredient("TMTO","Diced Tomatoes", tacos.Ingredient.Type.VEGGIES),
					new Ingredient("LETC","Lettuce", tacos.Ingredient.Type.VEGGIES),
					new Ingredient("CHED","Cheddar", tacos.Ingredient.Type.CHEESE),
					new Ingredient("JACK","Monterrey Jack", tacos.Ingredient.Type.CHEESE),
					new Ingredient("SLSA","Salsa", tacos.Ingredient.Type.SAUCE),
					new Ingredient("SRCR","Sour Cream", tacos.Ingredient.Type.SAUCE)				
				);
		Type[] types = Ingredient.Type.values();
		for (Type type : types ) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,type));
		}
		
		model.addAttribute("design", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processDesign(Design design) {
		//Save the Taco design
		// in chapter 3
		log.info("Processing design: " + design);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

	    return ingredients.stream()
	            .filter(x -> x.getType().equals(type))
	            .collect(Collectors.toList());

	}

}
