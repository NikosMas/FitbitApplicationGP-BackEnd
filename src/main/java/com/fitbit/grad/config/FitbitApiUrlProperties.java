package com.fitbit.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * properties for api urls
 * 
 * @author nikosmas, alex_kak
 */
@ConfigurationProperties("fitbitApiUrls")
public class FitbitApiUrlProperties {

	private String timeInBedUrl;
	private String minutesAsleepUrl;
	private String minutesAwakeUrl;
	private String toFallAsleepUrl;
	private String afterWakeUpUrl;
	private String efficiencyUrl;
	private String stepsUrl;
	private String floorsUrl;
	private String distanceUrl;
	private String caloriesUrl;
	private String heartUrl;
	private String profileUrl;
	private String lifetimeUrl;
	private String frequenceUrl;
	
	public String getTimeInBedUrl() {
		return timeInBedUrl;
	}
	public void setTimeInBedUrl(String timeInBedUrl) {
		this.timeInBedUrl = timeInBedUrl;
	}
	public String getMinutesAsleepUrl() {
		return minutesAsleepUrl;
	}
	public void setMinutesAsleepUrl(String minutesAsleepUrl) {
		this.minutesAsleepUrl = minutesAsleepUrl;
	}
	public String getMinutesAwakeUrl() {
		return minutesAwakeUrl;
	}
	public void setMinutesAwakeUrl(String minutesAwakeUrl) {
		this.minutesAwakeUrl = minutesAwakeUrl;
	}
	public String getToFallAsleepUrl() {
		return toFallAsleepUrl;
	}
	public void setToFallAsleepUrl(String toFallAsleepUrl) {
		this.toFallAsleepUrl = toFallAsleepUrl;
	}
	public String getAfterWakeUpUrl() {
		return afterWakeUpUrl;
	}
	public void setAfterWakeUpUrl(String afterWakeUpUrl) {
		this.afterWakeUpUrl = afterWakeUpUrl;
	}
	public String getEfficiencyUrl() {
		return efficiencyUrl;
	}
	public void setEfficiencyUrl(String efficiencyUrl) {
		this.efficiencyUrl = efficiencyUrl;
	}
	public String getStepsUrl() {
		return stepsUrl;
	}
	public void setStepsUrl(String stepsUrl) {
		this.stepsUrl = stepsUrl;
	}
	public String getFloorsUrl() {
		return floorsUrl;
	}
	public void setFloorsUrl(String floorsUrl) {
		this.floorsUrl = floorsUrl;
	}
	public String getDistanceUrl() {
		return distanceUrl;
	}
	public void setDistanceUrl(String distanceUrl) {
		this.distanceUrl = distanceUrl;
	}
	public String getCaloriesUrl() {
		return caloriesUrl;
	}
	public void setCaloriesUrl(String caloriesUrl) {
		this.caloriesUrl = caloriesUrl;
	}
	public String getHeartUrl() {
		return heartUrl;
	}
	public void setHeartUrl(String heartUrl) {
		this.heartUrl = heartUrl;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getLifetimeUrl() {
		return lifetimeUrl;
	}
	public void setLifetimeUrl(String lifetimeUrl) {
		this.lifetimeUrl = lifetimeUrl;
	}
	public String getFrequenceUrl() {
		return frequenceUrl;
	}
	public void setFrequenceUrl(String frequenceUrl) {
		this.frequenceUrl = frequenceUrl;
	}
	
}
