class Class
  def attr_accessor_with_history(attr_name)
    attr_name = attr_name.to_s
    attr_reader attr_name

    define_method "#{attr_name}_history" do
      instance_variable_get("@#{attr_name}_history") || [nil]
    end

    define_method "#{attr_name}=" do |new_value|
      v = instance_variable_get("@#{attr_name}_history")
      v ||= [nil]
      v << new_value

      instance_variable_set("@#{attr_name}_history", v)
      instance_variable_set("@#{attr_name}", new_value)
    end
  end
end